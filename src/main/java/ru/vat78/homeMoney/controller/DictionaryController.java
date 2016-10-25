package ru.vat78.homeMoney.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.tools.ColumnDefinition;
import ru.vat78.homeMoney.model.tools.UserTableSettings;
import ru.vat78.homeMoney.service.SecurityService;
import ru.vat78.homeMoney.service.DictionaryService;
import ru.vat78.homeMoney.service.UserSettingsService;

import java.util.*;

@Controller
@Secured({"ROLE_USER","ROLE_ADMIN"})
@RequestMapping("/dictionaries")
public class DictionaryController {

    @Autowired
    DictionaryService dictionaryService;

    @Autowired
    SecurityService securityService;

    @Autowired
    UserSettingsService userSettingsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showDefaultPage(){
        return new ModelAndView("dictionaries");
    }

    @RequestMapping(value = "view/{name}", method = RequestMethod.GET)
    public ModelAndView showDictionaryPage(@PathVariable String name){

        if (dictionaryService.isTypeExist(name)){

            ModelAndView mv;
            if (name.equals(Defenitions.TABLES.CATEGORIES)) {
                mv = new ModelAndView("tree_dictionary");
            } else {
                mv = new ModelAndView("simple_dictionary");
            }
            return prepareDictionaryPage(name, mv);
        }

        return showDefaultPage();
    }

    private ModelAndView prepareDictionaryPage(String dictionary, ModelAndView mv){

        UserTableSettings settings = userSettingsService.getTableSettings(securityService.getCurrentUser(),dictionary);
        mv.addObject("tableDef", settings);

        TreeSet<ColumnDefinition> columns = new TreeSet<ColumnDefinition>();
        columns.addAll(settings.getColumns().values());
        mv.addObject("columns", columns);

        return mv;
    }

}
