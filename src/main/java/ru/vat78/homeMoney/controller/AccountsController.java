package ru.vat78.homeMoney.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.tools.ColumnDefinition;
import ru.vat78.homeMoney.model.tools.UserTableSettings;
import ru.vat78.homeMoney.service.AccountsService;
import ru.vat78.homeMoney.service.DictionaryService;
import ru.vat78.homeMoney.service.SecurityService;
import ru.vat78.homeMoney.service.UserSettingsService;

import java.util.*;

@Controller
@Secured({"ROLE_USER","ROLE_ADMIN"})
@RequestMapping("/accounts")
public class AccountsController {

    @Autowired
    AccountsService accountsService;

    @Autowired
    DictionaryService dictionaryService;

    @Autowired
    SecurityService securityService;

    @Autowired
    UserSettingsService userSettingsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showAccountsPage(@RequestParam(defaultValue = Defenitions.TABLES.ACCOUNTS) String type){
        ModelAndView result = new ModelAndView("accounts");
        result.addObject("accountTypes", accountsService.getAccountsTypes());
        result.addObject("currencies", dictionaryService.getRecords(Defenitions.TABLES.CURRENCY,0,100, Defenitions.FIELDS.NAME,"asc",""));
        result.addObject("dateFormat", "dd.mm.yyyy");
        return prepareAccountPage(type,result);
    }

    private ModelAndView prepareAccountPage(String tableName, ModelAndView mv){

        UserTableSettings settings = userSettingsService.getTableSettings(securityService.getCurrentUser(),tableName);
        settings.setAddBtn(!(tableName.equals(Defenitions.TABLES.ACCOUNTS) || tableName.equals("closed")));
        mv.addObject("tableDef", settings);

        TreeSet<ColumnDefinition> columns = new TreeSet<ColumnDefinition>();
        columns.addAll(settings.getColumns().values());
        mv.addObject("columns", columns);

        return mv;
    }
}
