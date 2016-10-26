package ru.vat78.homeMoney.controller.api;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.User;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;
import ru.vat78.homeMoney.model.dictionaries.TreeDictionary;
import ru.vat78.homeMoney.service.DictionaryService;
import ru.vat78.homeMoney.service.SecurityService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@Secured({"ROLE_USER","ROLE_ADMIN"})
@RequestMapping("/api/dictionaries")
public class ApiDictionaryController {

    @Autowired
    DictionaryService dictionaryService;

    @Autowired
    SecurityService securityService;

    @Autowired
    MyGsonBuilder gsonBuilder;

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

        Gson gson = gsonBuilder.getGsonBuilder()
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

        Class entryType = dictionaryService.getDictionaryClass(allRequestParams.get("table"));
        if (entryType==null) return "";

        Set<Dictionary> result = dictionaryService.getTreeRecords(
                allRequestParams.get("table"),
                ApiTools.parseId(allRequestParams.get("id"))
        );
        if (result == null) result = Collections.emptySet();

        Gson gson = gsonBuilder.getGsonBuilder()
                .disableInnerClassSerialization()
                .serializeNulls()
                .registerTypeAdapter(entryType, GsonSerializerBuilder.getSerializer(TreeDictionary.class))
                .setDateFormat(Defenitions.DATE_FORMAT)
                .create();
        Response answer = new Response();
        answer.setStatus(Response.SUCCESS);
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
        if (result == null) return "";

        String arr[] = new String[result.size()];
        int i = 0;
        for (Dictionary element :  result) {
            arr[i] = element.getFullName();
            i++;
        }

        Gson gson = gsonBuilder.getGsonBuilder()
                .disableInnerClassSerialization()
                .serializeNulls()
                .create();
        return gson.toJson(arr);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String saveEntry(@RequestParam Map<String,String> allRequestParams){

        Response res = new Response();

        Dictionary entity = loadEntryFromParams(allRequestParams, res);

        ApiTools.validateEntry(entity, res);
        ApiTools.checkUniqueName(dictionaryService, entity, res);
        ApiTools.saveEntityToDb(dictionaryService, entity, res);

        Gson gson = gsonBuilder.getGsonBuilder().create();
        return gson.toJson(res);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteEntry(@RequestParam Map<String,String> allRequestParams){

        Response result = new Response();
        ApiTools.deleteEntry(dictionaryService, allRequestParams.get("table"), allRequestParams.get("id"), result);

        return gsonBuilder.getGsonBuilder().create().toJson(result);
    }


    @RequestMapping(value = "/tsave", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String saveTreeEntry(@RequestParam Map<String,String> allRequestParams){

        Response result = new Response();

        TreeDictionary entry = loadTreeEntryFromParams(allRequestParams, result);

        ApiTools.validateEntry(entry, result);
        ApiTools.checkUniqueName(dictionaryService, entry, result);
        ApiTools.saveEntityToDb(dictionaryService, entry, result);

        Gson gson = gsonBuilder.getGsonBuilder()
                .disableInnerClassSerialization()
                .serializeNulls()
                .registerTypeAdapter(entry.getClass(), GsonSerializerBuilder.getSerializer(TreeDictionary.class))
                .setDateFormat(Defenitions.DATE_FORMAT)
                .create();

        return gson.toJson(result);
    }

    private Dictionary loadEntryFromParams(Map<String,String> params, Response response){

        Dictionary result = (Dictionary) ApiTools.buildEntryFromParams(dictionaryService, params, response);

        if (result != null) {
            result.setModifyBy(securityService.getCurrentUser());
        }

        return result;
    }

    private TreeDictionary loadTreeEntryFromParams(Map<String,String> params, Response response){

        TreeDictionary result = (TreeDictionary) ApiTools.buildEntryFromParams(dictionaryService, params, response);

        if (result != null){

            Long parentId = ApiTools.parseId(params.get("parent"));

            if (parentId == 0) {

                result.setParent(null);

            } else {

                TreeDictionary parent = (TreeDictionary) dictionaryService.getRecordById(result.getType(), parentId);
                result.setParent(parent);
            }
        }

        return result;
    }
}
