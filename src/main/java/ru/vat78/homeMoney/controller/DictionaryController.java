package ru.vat78.homeMoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.tools.UserColumnsSettings;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dictionaries")
public class DictionaryController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showDefaultPage(){
        ModelAndView mv = new ModelAndView("dictionaries");
        return mv;
    }

    @RequestMapping(params = "tags", method = RequestMethod.GET)
    public ModelAndView showTags(){
        ModelAndView mv = new ModelAndView("simple_dictionary");
        mv.addObject("tableName", "tags");
        mv.addObject("dictionaryName", "Tags");
        mv.addObject("columns", defaultDictionaryColumns(Defenitions.TABLES.TAGS));
        return mv;
    }

    private List<UserColumnsSettings> defaultDictionaryColumns(String dictionaryName){

        List<UserColumnsSettings> result = new ArrayList<UserColumnsSettings>(7);
        result.add(new UserColumnsSettings()
                .setTableName(dictionaryName)
                .setColumnName("id")
                .setNum(1)
                .setVisible(false));
        result.add(new UserColumnsSettings()
                .setTableName(dictionaryName)
                .setColumnName("name")
                .setNum(2)
                .setVisible(true));
        result.add(new UserColumnsSettings()
                .setTableName(dictionaryName)
                .setColumnName("searchName")
                .setNum(3)
                .setVisible(false));
        result.add(new UserColumnsSettings()
                .setTableName(dictionaryName)
                .setColumnName("createOn")
                .setNum(4)
                .setVisible(false));
        result.add(new UserColumnsSettings()
                .setTableName(dictionaryName)
                .setColumnName("createBy")
                .setNum(5)
                .setVisible(false));
        result.add(new UserColumnsSettings()
                .setTableName(dictionaryName)
                .setColumnName("modifyOn")
                .setNum(6)
                .setVisible(false));
        result.add(new UserColumnsSettings()
                .setTableName(dictionaryName)
                .setColumnName("modifyBy")
                .setNum(7)
                .setVisible(false));

        return result;
    }
}
