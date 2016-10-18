package ru.vat78.homeMoney.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.User;
import ru.vat78.homeMoney.model.accounts.Account;
import ru.vat78.homeMoney.model.tools.ColumnDefinition;
import ru.vat78.homeMoney.model.tools.UserTableSettings;
import ru.vat78.homeMoney.model.transactions.Bill;
import ru.vat78.homeMoney.model.transactions.Transaction;
import ru.vat78.homeMoney.model.transactions.Transfer;
import ru.vat78.homeMoney.service.AccountsService;
import ru.vat78.homeMoney.service.SecurityService;
import ru.vat78.homeMoney.service.TransactionsService;
import ru.vat78.homeMoney.service.UserSettingsService;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

@Controller
@Secured({"ROLE_USER","ROLE_ADMIN"})
@RequestMapping("/transactions")
public class TransactionsController {

    @Autowired
    TransactionsService transactionsService;

    @Autowired
    AccountsService accountsService;

    @Autowired
    SecurityService securityService;

    @Autowired
    UserSettingsService userSettingsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showTransactionsPage(@RequestParam long account){
        ModelAndView result = new ModelAndView("transactions");
        result.addObject("accountTypes", accountsService.getAccountsTypes());
        result.addObject("account", account);
        result.addObject("dateFormat", "dd.mm.yyyy");
        return prepareTransactionsPage(account,result);
    }

    @RequestMapping(value = "/data.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getTable(@RequestParam Map<String,String> allRequestParams){

        long accountId = strToLong(allRequestParams.get("account"));
        Account account = accountsService.getAccountById(accountId);

        List<Transaction> list;
        list = transactionsService.getTransactionsByAccount(
                account,
                Integer.valueOf(allRequestParams.get("offset")),
                Integer.valueOf(allRequestParams.get("limit")),
                allRequestParams.get("sort"),allRequestParams.get("order"),
                allRequestParams.get("search")
        );

        Gson gson = new GsonBuilder()
                .disableInnerClassSerialization()
                .serializeNulls()
                .registerTypeAdapter(User.class, GsonSerializerBuilder.getSerializer(User.class))
                .setDateFormat(Defenitions.DATE_FORMAT)
                .create();
        return gson.toJson(list);
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public ModelAndView showTransferEditForm(@RequestParam Map<String,String> allRequestParams){

        ModelAndView result;
        result = new ModelAndView("transfer");

        prepareEditForm(allRequestParams, result, getTransferForModel(allRequestParams.get("id")));

        return result;
    }

    @RequestMapping(value = "/bill", method = RequestMethod.GET)
    public ModelAndView showBillEditForm(@RequestParam Map<String,String> allRequestParams){

        ModelAndView result;
        result = new ModelAndView("bill");

        prepareEditForm(allRequestParams, result, getBillForModel(allRequestParams.get("id")));
        result.addObject("contractorTable", Defenitions.TABLES.CONTRACTORS);

        return result;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String saveEntry(@RequestParam Map<String,String> allRequestParams){

        Response res = new Response();

        Transaction entity = loadEntryFromParams(allRequestParams, res);

        if (res.getResult() == null) {
            if (transactionsService.saveRecord(entity)) {
                res.setStatus("SUCCESS");
                res.setResult(entity);
            } else {
                res.setError("name", "Can't save to database.");
            }
        }

        Gson gson = new GsonBuilder().create();
        return gson.toJson(res);
    }

    private Transaction loadEntryFromParams(Map<String, String> data, Response response) {

        Transaction result = transactionsService.getNewEntry(data.get(Defenitions.FIELDS.OPERATION_TYPE));
        if (result == null){
            response.setError(Defenitions.FIELDS.ACCOUNT_ID, "Wrong operation type");
            return null;
        }

        WebDataBinder binder = new WebDataBinder(result);
        binder.bind(new MutablePropertyValues(data));
        if (result.getSum() == 0) {
            response.setError(Defenitions.FIELDS.SUM, "Please, enter the sum of transaction");
        }

        if (!result.setDate(data.get(Defenitions.FIELDS.DATE),Defenitions.DATE_FORMAT)){
            response.setError(Defenitions.FIELDS.DATE, "Wrong date of transaction");
        }

        result.setAccount(accountsService.getAccountById(strToLong(data.get(Defenitions.FIELDS.ACCOUNT_ID))));
        if (result.getAccount() == null) {
            response.setError(Defenitions.FIELDS.ACCOUNT_ID, "Please, select account");
        }

        if (data.get(Defenitions.FIELDS.OPERATION_TYPE).equals(Defenitions.TABLES.TRANSFERS)){
            Transfer transfer = (Transfer) result;
            transfer.setCorrAccount(accountsService.getAccountById(strToLong(data.get(Defenitions.FIELDS.CORRESPONDING_ACCOUNT))));
            if (transfer.getCorrAccount() == null || transfer.getCorrAccount().getId() == transfer.getAccount().getId()) {
                response.setError(Defenitions.FIELDS.CORRESPONDING_ACCOUNT, "Wrong corresponding account");
            }
        }
        return result;
    }

    private ModelAndView prepareTransactionsPage(long account, ModelAndView view) {

        UserTableSettings settings = userSettingsService.getTableSettings(securityService.getCurrentUser(),Defenitions.TABLES.TRANSACTIONS + account);
        view.addObject("tableDef", settings);

        TreeSet<ColumnDefinition> columns = new TreeSet<ColumnDefinition>();
        columns.addAll(settings.getColumns().values());
        view.addObject("columns", columns);

        return view;
    }

    private long strToLong(String value){
        if (value == null) return 0;
        long result = 0;
        try {
            result = Long.valueOf(value);
        } catch (Exception e) {}
        return result;
    }

    private Transaction getTransferForModel(String transferId){

        long entryId = strToLong(transferId);
        Transfer entry = null;
        if (entryId != 0 ) {
            entry = (Transfer) transactionsService.getTransactionById(entryId);
        } else {
            entry = (Transfer) transactionsService.getNewEntry(Defenitions.TABLES.TRANSFERS);
        }
        return entry;
    }

    private Transaction getBillForModel(String billId){

        long entryId = strToLong(billId);
        Bill entry = null;
        if (entryId != 0 ) {
            entry = (Bill) transactionsService.getTransactionById(entryId);
        } else {
            entry = (Bill) transactionsService.getNewEntry(Defenitions.TABLES.BILLS);
        }
        return entry;
    }

    private void prepareEditForm(Map<String,String> params, ModelAndView mv, Transaction entry){

        long accountId = strToLong(params.get("account"));
        Account account = accountsService.getAccountById(accountId);
        if (account == null) {
            mv = showTransactionsPage(accountId);
            return;
        }

        if (entry.getId() == null || entry.getId() == 0) {
            mv.addObject("edit", 0);
        } else {
            mv.addObject("edit", 1);
        }
        entry.setDefaultValues(account);
        mv.addObject("entry", entry);

        mv.addObject("dateFormat", "dd.MM.yyyy");
        mv.addObject("account", accountId);
        mv.addObject("accounts", accountsService.getAllAccounts(true));
        mv.addObject("accountTypes", accountsService.getAccountsTypes());
    }
}

