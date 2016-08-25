package ru.vat78.homeMoney.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.User;
import ru.vat78.homeMoney.model.accounts.SimpleAccount;
import ru.vat78.homeMoney.model.tools.ColumnDefinition;
import ru.vat78.homeMoney.model.tools.UserTableSettings;
import ru.vat78.homeMoney.model.transactions.Transaction;
import ru.vat78.homeMoney.model.transactions.Transfer;
import ru.vat78.homeMoney.service.AccountsService;
import ru.vat78.homeMoney.service.SecurityService;
import ru.vat78.homeMoney.service.TransactionsService;
import ru.vat78.homeMoney.service.UserSettingsService;

import java.util.Date;
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
        SimpleAccount account = accountsService.getAccountById(accountId);

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
    public ModelAndView showEditForm(@RequestParam Map<String,String> allRequestParams){

        long accountId = strToLong(allRequestParams.get("account"));
        SimpleAccount account = accountsService.getAccountById(accountId);
        if (account == null) return showTransactionsPage(accountId);

        long entryId = strToLong(allRequestParams.get("id"));

        ModelAndView result;
        result = new ModelAndView("transfer");
        result.addObject("dateFormat", "dd.mm.yyyy");
        result.addObject("account", accountId);
        insertTransferToModel(result, entryId, account);
        result.addObject("accounts", accountsService.getAllAccounts(true));
        result.addObject("accountTypes", accountsService.getAccountsTypes());

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

    private void insertTransferToModel(ModelAndView mv, long transferId, SimpleAccount account){

        Transfer entry = null;
        if (transferId != 0 ) {
            mv.addObject("edit", 1);
            entry = (Transfer) transactionsService.getTransactionById(transferId);
        } else {
            mv.addObject("edit", 0);
            entry = (Transfer) transactionsService.getNewEntry(Defenitions.TABLES.TRANSFERS);
            entry.setDefaultValues(account);
        }
        mv.addObject("entry",entry);
    }
}

