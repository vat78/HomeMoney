package ru.vat78.homeMoney.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.vat78.homeMoney.dao.dictionaries.DictionaryDao;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;
import ru.vat78.homeMoney.model.dictionaries.Tag;
import ru.vat78.homeMoney.model.tools.UserColumnsSettings;
import ru.vat78.homeMoney.service.SimpleDictionaryService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dictionaries")
public class DictionaryController {

    @Autowired
    SimpleDictionaryService simpleDictionaryService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showDefaultPage(){
        ModelAndView mv = new ModelAndView("dictionaries");
        return mv;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ModelAndView showTags(@PathVariable String name){

        if (!simpleDictionaryService.checkDictionaryName(name)){
            return showDefaultPage();
        }

        ModelAndView mv = new ModelAndView("simple_dictionary");
        mv.addObject("tableName", name);
        mv.addObject("dictionaryName", name);
        mv.addObject("columns", defaultDictionaryColumns(name));
        return mv;
    }

    @RequestMapping(value = "/ajax", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getTagsTable(@RequestParam Map<String,String> allRequestParams){

        List<Dictionary> result = simpleDictionaryService.getRecords(
                allRequestParams.get("table"),
                Integer.valueOf(allRequestParams.get("offset")),
                Integer.valueOf(allRequestParams.get("limit")),
                allRequestParams.get("sort"),allRequestParams.get("order"));
        if (result == null) result = Collections.emptyList();
        Gson gson = new Gson();
        String gres = gson.toJson(result);
        return gson.toJson(result);
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
