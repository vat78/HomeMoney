package ru.vat78.homeMoney.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.accounts.Account;
import ru.vat78.homeMoney.model.tools.UIElement;
import ru.vat78.homeMoney.model.transactions.Bill;
import ru.vat78.homeMoney.model.transactions.Transaction;
import ru.vat78.homeMoney.model.transactions.Transfer;
import ru.vat78.homeMoney.service.AccountsService;
import ru.vat78.homeMoney.service.SecurityService;
import ru.vat78.homeMoney.service.TransactionsService;
import ru.vat78.homeMoney.service.UserSettingsService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    private ModelAndView prepareTransactionsPage(long account, ModelAndView view) {

        UIElement settings = userSettingsService.getTableSettings(securityService.getCurrentUser(),Defenitions.TABLES.TRANSACTIONS + account);
        view.addObject("tableDef", settings);

        Set<UIElement> columns = new HashSet<UIElement>();
        columns.addAll(settings.getChildren());
        view.addObject("columns", columns);

        return view;
    }

    private long strToLong(String value){
        if (value == null) return 0;
        long result = 0;
        try {
            result = Long.valueOf(value);
        } catch (Exception ignored) {}
        return result;
    }

    private Transaction getTransferForModel(String transferId){

        long entryId = strToLong(transferId);
        Transfer entry;
        if (entryId != 0 ) {
            entry = (Transfer) transactionsService.getRecordById(entryId);
        } else {
            entry = (Transfer) transactionsService.getNewEntry(Defenitions.TABLES.TRANSFERS);
        }
        return entry;
    }

    private Transaction getBillForModel(String billId){

        long entryId = strToLong(billId);
        Bill entry;
        if (entryId != 0 ) {
            entry = (Bill) transactionsService.getRecordById(entryId);
        } else {
            entry = (Bill) transactionsService.getNewEntry(Defenitions.TABLES.BILLS);
        }
        return entry;
    }

    private void prepareEditForm(Map<String,String> params, ModelAndView mv, Transaction entry){

        long accountId = strToLong(params.get("account"));
        Account account = accountsService.getRecordById(accountId);
        if (account == null) {
            showTransactionsPage(accountId);
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

