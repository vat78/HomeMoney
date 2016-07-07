package ru.vat78.homeMoney.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.vat78.homeMoney.model.User;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;
import ru.vat78.homeMoney.model.tools.ColumnDefinition;
import ru.vat78.homeMoney.model.tools.UserTableSettings;
import ru.vat78.homeMoney.service.SecurityService;
import ru.vat78.homeMoney.service.SimpleDictionaryService;
import ru.vat78.homeMoney.service.UserSettingsService;

import javax.validation.*;
import java.util.*;

@Controller
@Secured({"ROLE_USER","ROLE_ADMIN"})
@RequestMapping("/dictionaries")
public class DictionaryController {

    @Autowired
    SimpleDictionaryService simpleDictionaryService;

    @Autowired
    SecurityService securityService;

    @Autowired
    UserSettingsService userSettingsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showDefaultPage(){
        return new ModelAndView("dictionaries");
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ModelAndView showDictionaryPage(@PathVariable String name){

        if (!simpleDictionaryService.checkDictionaryName(name)){
            return showDefaultPage();
        }

        ModelAndView mv = new ModelAndView("simple_dictionary");

        UserTableSettings settings = userSettingsService.getTableSettings(securityService.getCurrentUser(),name);
        mv.addObject("tableDef", settings);
        TreeSet<ColumnDefinition> columns = new TreeSet<ColumnDefinition>();
        columns.addAll(settings.getColumns().values());
        mv.addObject("columns", columns);
        //mv.addObject("tableName", name);
        //mv.addObject("dictionaryName", name);
        //mv.addObject("columns", defaultDictionaryColumns(name));
        //mv.addObject("form", getEditingColumns(name));
        return mv;
    }

    @RequestMapping(value = "/data.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getTable(@RequestParam Map<String,String> allRequestParams){

        List<Dictionary> result = simpleDictionaryService.getRecords(
                allRequestParams.get("table"),
                Integer.valueOf(allRequestParams.get("offset")),
                Integer.valueOf(allRequestParams.get("limit")),
                allRequestParams.get("sort"),allRequestParams.get("order"),
                allRequestParams.get("search"));
        if (result == null) result = Collections.emptyList();

        TableForJson table = new TableForJson();
        table.setTotal(simpleDictionaryService.getCount(allRequestParams.get("table")));
        table.setRows(result);

        Gson gson = new GsonBuilder()
                .disableInnerClassSerialization()
                .serializeNulls()
                .registerTypeAdapter(User.class, GsonSerializerBuilder.getSerializer(User.class))
                .setDateFormat("dd.MM.yyyy")
                .create();
        String res = gson.toJson(table);
        return gson.toJson(table);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String saveEntry(@RequestParam Map<String,String> allRequestParams){

        Response res = new Response();

        Dictionary entity = loadEntryFromParams(allRequestParams);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set result = validator.validate(entity);

        if (result.size() > 0) {
            res.setStatus("FAIL");
            List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
            for (Object val : result){
                ConstraintViolation error = (ConstraintViolation) val;
                errorMesages.add(new ErrorMessage(error.getPropertyPath().toString(), error.getMessage()));
            }
            res.setResult(errorMesages);
        }

        checkUniqueName(allRequestParams.get("table"), entity, res);
        saveEntityToDb(allRequestParams.get("table"), entity, res);

        Gson gson = new GsonBuilder().create();
        return gson.toJson(res);
    }

    private Dictionary loadEntryFromParams(Map<String,String> params){

        Dictionary result = simpleDictionaryService.getNewEntry(params.get("table"));
        WebDataBinder binder = new WebDataBinder(result);
        binder.bind(new MutablePropertyValues(params));
        result.setModifyBy(securityService.getCurrentUser());

        return result;
    }

    private void checkUniqueName(String dictionary, Dictionary entity, Response response){

        if (response.getResult() == null) {
            Dictionary inDB = simpleDictionaryService.getRecordByName(dictionary, entity.getName());
            if (inDB != null && inDB.getId() != entity.getId()){
                response.setStatus("FAIL");
                List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
                errorMesages.add(new ErrorMessage("name", "Such name already exists"));
                response.setResult(errorMesages);
            }
        }
    }

    private void saveEntityToDb(String dictionary, Dictionary entity, Response response) {

        if (response.getResult() == null) {
            if (simpleDictionaryService.saveRecord(dictionary,entity)) {
                response.setStatus("SUCCESS");
            } else {
                response.setStatus("FAIL");
                List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
                errorMesages.add(new ErrorMessage("name", "Can't save to database."));
                response.setResult(errorMesages);
            }
        }
    }

    /*
    private List<UserTableSettings> defaultDictionaryColumns(String dictionaryName){

        List<UserTableSettings> result = new ArrayList<UserTableSettings>(7);
        result.add(new UserTableSettings()
                .setTableName(dictionaryName)
                .setColumnName("id")
                .setNum(1)
                .setVisible(false));
        result.add(new UserTableSettings()
                .setTableName(dictionaryName)
                .setColumnName("name")
                .setNum(2)
                .setVisible(true));

        result.add(new UserTableSettings()
                .setTableName(dictionaryName)
                .setColumnName("createOn")
                .setNum(4)
                .setVisible(false));
        result.add(new UserTableSettings()
                .setTableName(dictionaryName)
                .setColumnName("createBy")
                .setNum(5)
                .setVisible(false));
        result.add(new UserTableSettings()
                .setTableName(dictionaryName)
                .setColumnName("modifyOn")
                .setNum(6)
                .setVisible(false));
        result.add(new UserTableSettings()
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
    */
}
