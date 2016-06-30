package ru.vat78.homeMoney.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;
import ru.vat78.homeMoney.model.tools.ColumnDefinition;
import ru.vat78.homeMoney.model.tools.UserColumnsSettings;
import ru.vat78.homeMoney.service.SecurityService;
import ru.vat78.homeMoney.service.SimpleDictionaryService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@Secured({"ROLE_USER","ROLE_ADMIN"})
@RequestMapping("/dictionaries")
public class DictionaryController {

    @Autowired
    SimpleDictionaryService simpleDictionaryService;

    @Autowired
    SecurityService securityService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showDefaultPage(){
        ModelAndView mv = new ModelAndView("dictionaries");
        return mv;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ModelAndView showDictionaryPage(@PathVariable String name){

        if (!simpleDictionaryService.checkDictionaryName(name)){
            return showDefaultPage();
        }

        ModelAndView mv = new ModelAndView("simple_dictionary");
        mv.addObject("tableName", name);
        mv.addObject("dictionaryName", name);
        mv.addObject("columns", defaultDictionaryColumns(name));
        mv.addObject("form", getEditingColumns(name));
        return mv;
    }

    @RequestMapping(value = "/ajax", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getTable(@RequestParam Map<String,String> allRequestParams){

        List<Dictionary> result = simpleDictionaryService.getRecords(
                allRequestParams.get("table"),
                Integer.valueOf(allRequestParams.get("offset")),
                Integer.valueOf(allRequestParams.get("limit")),
                allRequestParams.get("sort"),allRequestParams.get("order"));
        if (result == null) result = Collections.emptyList();

        TableForJson table = new TableForJson();
        table.setTotal(simpleDictionaryService.getCount(allRequestParams.get("table")));
        table.setRows(result);

        Gson gson = new Gson();
        String gres = gson.toJson(table);
        return gson.toJson(table);
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String isNameOfEntryUnique(@RequestParam Map<String,String> allRequestParams){
        if (allRequestParams.get("table") == null) return Defenitions.FALSE;
        if (allRequestParams.get("name") == null) return Defenitions.FALSE;
        if (simpleDictionaryService.getRecordByName(
                allRequestParams.get("table"),
                allRequestParams.get("name")
            ) != null) return Defenitions.FALSE;
        return Defenitions.TRUE;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveEntry(@RequestParam Map<String,String> allRequestParams){

        if (isNameOfEntryUnique(allRequestParams) == Defenitions.FALSE) return null;

        Dictionary entity = simpleDictionaryService.convertToDBObject(allRequestParams.get("table"),allRequestParams);
        if (entity == null) return null;

        entity.setModifyBy(securityService.getCurrentUser());

        if (!simpleDictionaryService.saveRecord(allRequestParams.get("table"),entity)) return null;
        return "ok";
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

    private List<ColumnDefinition> getEditingColumns(String dictionary){
        List<ColumnDefinition> result = new ArrayList<ColumnDefinition>();
        result.add(new ColumnDefinition()
                .setName("name")
                .setCaption("Name")
                .setRequired(true)
        );

        return result;
    }
}
