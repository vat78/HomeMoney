package ru.vat78.homeMoney.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.User;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;
import ru.vat78.homeMoney.model.dictionaries.TreeDictionary;
import ru.vat78.homeMoney.model.tools.ColumnDefinition;
import ru.vat78.homeMoney.model.tools.UserTableSettings;
import ru.vat78.homeMoney.service.SecurityService;
import ru.vat78.homeMoney.service.DictionaryService;
import ru.vat78.homeMoney.service.UserSettingsService;

import javax.validation.*;
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

        if (dictionaryService.checkDictionaryName(name)){

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

    @RequestMapping(value = "/data.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getTable(@RequestParam Map<String,String> allRequestParams){

        List<Dictionary> result = dictionaryService.getRecords(
                allRequestParams.get("table"),
                Integer.valueOf(allRequestParams.get("offset")),
                Integer.valueOf(allRequestParams.get("limit")),
                allRequestParams.get("sort"),allRequestParams.get("order"),
                allRequestParams.get("search"));
        if (result == null) result = Collections.emptyList();

        TableForJson table = new TableForJson();
        table.setTotal(dictionaryService.getCount(allRequestParams.get("table")));
        table.setRows(result);

        Gson gson = new GsonBuilder()
                .disableInnerClassSerialization()
                .serializeNulls()
                .registerTypeAdapter(User.class, GsonSerializerBuilder.getSerializer(User.class))
                .setDateFormat(Defenitions.DATE_FORMAT)
                .create();
        return gson.toJson(table);
    }

    @RequestMapping(value = "/tree.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getTree(@RequestParam Map<String,String> allRequestParams){

        Class entryType = dictionaryService.getEntityClass(allRequestParams.get("table"));
        if (entryType==null) return "";
        if (allRequestParams.get("id") == null) allRequestParams.put("id", "0");

        List<Dictionary> result = dictionaryService.getTreeRecords(
                allRequestParams.get("table"),
                Long.valueOf(allRequestParams.get("id"))
        );
        if (result == null) result = Collections.emptyList();

        Gson gson = new GsonBuilder()
                .disableInnerClassSerialization()
                .serializeNulls()
                .registerTypeAdapter(entryType, GsonSerializerBuilder.getSerializer(TreeDictionary.class))
                .setDateFormat(Defenitions.DATE_FORMAT)
                .create();
        Response answer = new Response();
        answer.setStatus("SUCCESS");
        answer.setResult(result);

        return gson.toJson(answer);
    }

    @RequestMapping(value = "/typeahead.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getValuesForFields(@RequestParam Map<String,String> allRequestParams){

        if (allRequestParams.get("table") == null || allRequestParams.get("term") == null || allRequestParams.get("term").length() == 1)
            return "";

        List<Dictionary> result = dictionaryService.getRecords(
                allRequestParams.get("table"),
                0,
                15,
                Defenitions.FIELDS.NAME,"asc",
                allRequestParams.get("term"));
        if (result == null) result = Collections.emptyList();

        String arr[] = new String[result.size()];
        int i = 0;
        for (Dictionary element :  result) {
            arr[i] = element.getName();
            i++;
        }
        Gson gson = new GsonBuilder()
                .disableInnerClassSerialization()
                .serializeNulls()
                .create();
        String test = gson.toJson(arr);
        return gson.toJson(arr);
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

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteEntry(@RequestParam Map<String,String> allRequestParams){
        Response result = new Response();
        result.setError("","May be it is used by other objects");

        String table = allRequestParams.get("table");
        String id = allRequestParams.get("id");
        if (table != null & id != null) {
            if (dictionaryService.deleteRecordById(table, Long.valueOf(id)))
                result.setStatus("SUCCESS");
        } else {
            result.setError("","There are no params 'id' and 'table'");
        }

        return new GsonBuilder().create().toJson(result);
    }

    @RequestMapping(value = "/tsave", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String saveTreeEntry(@RequestParam Map<String,String> allRequestParams){

        Response result = new Response();
        TreeDictionary entry = loadTreeEntryFromParams(allRequestParams, result);

        insertParentNode(allRequestParams, entry, result);
        checkUniqueName(allRequestParams.get("table"), entry, result);
        saveEntityToDb(allRequestParams.get("table"), entry, result);

        return new GsonBuilder().create().toJson(result);
    }

    @RequestMapping(value = "/tdelete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteTreeEntry(@RequestParam Map<String,String> allRequestParams){
        Response result = new Response();
        result.setStatus("FAIL");

        String table = allRequestParams.get("table");
        String id = allRequestParams.get("id");
        if (table != null & id != null) {
            if (dictionaryService.deleteRecordById(table, Long.valueOf(id)))
                result.setStatus("SUCCESS");
        } else {
            result.setError("","There are no params 'id' and 'table'");
        }

        return new GsonBuilder().create().toJson(result);
    }

    private Dictionary loadEntryFromParams(Map<String,String> params){

        Dictionary result = dictionaryService.getNewEntry(params.get("table"));
        WebDataBinder binder = new WebDataBinder(result);
        binder.bind(new MutablePropertyValues(params));
        result.setModifyBy(securityService.getCurrentUser());

        return result;
    }

    private void checkUniqueName(String dictionary, Dictionary entity, Response response){

        if (response.getResult() == null) {
            Dictionary inDB = dictionaryService.getRecordByName(dictionary, entity.getName());
            if (inDB != null && inDB.getId() != entity.getId()){
                response.setError("name", "Such name already exists");
            }
        }
    }

    private void saveEntityToDb(String dictionary, Dictionary entity, Response response) {

        if (response.getResult() == null) {
            if (dictionaryService.saveRecord(entity)) {
                response.setStatus("SUCCESS");
                response.setResult(entity);
            } else {
                response.setError("name", "Can't save to database.");
            }
        }
    }

    private void insertParentNode(Map<String,String> params, TreeDictionary entity, Response response) {

        if (response.getResult() == null) {

            long parentId = params.get("parent") == null ? 0 : Long.valueOf(params.get("parent"));
            if (parentId != 0) {
                TreeDictionary parent = (TreeDictionary) dictionaryService.getRecordById(params.get("table"), parentId);
                if (parent == null) {
                    response.setError("","Wrong parent");
                } else {
                    entity.setParent(parent);
                }
            } else {
                entity.setParent(null);
            }
        }
    }

    private TreeDictionary loadTreeEntryFromParams(Map<String,String> params, Response response){

        long id = params.get("id") == null ? 0 : Long.valueOf(params.get("id"));
        TreeDictionary entry = (TreeDictionary) dictionaryService.getRecordById(params.get("table"), id);
        if (entry == null) {
            entry = (TreeDictionary) dictionaryService.getNewEntry(params.get("table"));
        }

        if (entry == null) {
            response.setError("","Wrong table");
        } else {
            entry.setName(params.get("name"));
            entry.setModifyBy(securityService.getCurrentUser());
        }

        return entry;
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
