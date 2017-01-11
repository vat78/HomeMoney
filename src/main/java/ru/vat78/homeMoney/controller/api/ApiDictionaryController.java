package ru.vat78.homeMoney.controller.api;

import com.google.gson.Gson;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.vat78.homeMoney.controller.ControlTerms;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;
import ru.vat78.homeMoney.model.dictionaries.TreeDictionary;
import ru.vat78.homeMoney.model.exceptions.DbOperationException;
import ru.vat78.homeMoney.model.exceptions.ValidationException;
import ru.vat78.homeMoney.model.exceptions.WrongTypeException;
import ru.vat78.homeMoney.service.DictionaryService;
import ru.vat78.homeMoney.service.SecurityService;

import java.util.*;

@Controller
@Secured({"ROLE_USER","ROLE_ADMIN"})
@RequestMapping(ControlTerms.API_DICTIONARIES)
public class ApiDictionaryController {

    @Autowired
    DictionaryService dictionaryService;

    @Autowired
    SecurityService securityService;

    @Autowired
    MyGsonBuilder gsonBuilder;

    @RequestMapping(value = "/{objectType}/{objectId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Dictionary> getElement(@PathVariable String objectType,
                                                 @PathVariable Long objectId) throws WrongTypeException {

        Dictionary result = dictionaryService.getRecordById(objectType, objectId);
        //ToDo: is it right to make emty object?
        if (result == null) result = dictionaryService.getNewEntry(objectType);

        return new ResponseEntity<Dictionary>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{objectType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TableForJson> getTable(@PathVariable String objectType,
                                                 @RequestParam Map<String,String> allRequestParams) throws WrongTypeException {

        List<Dictionary> result = dictionaryService.getRecords(
                objectType,
                Integer.valueOf(allRequestParams.get(ControlTerms.DATA_OFFSET)),
                Integer.valueOf(allRequestParams.get(ControlTerms.DATA_LIMIT)),
                allRequestParams.get(ControlTerms.SORT_COLUMN),allRequestParams.get(ControlTerms.SORT_ORDER),
                allRequestParams.get(ControlTerms.SEARCH_STRING));
        if (result == null) result = Collections.emptyList();

        TableForJson table = new TableForJson();
        table.setTotal(dictionaryService.getCount(objectType));
        table.setRows(result);

        return new ResponseEntity<TableForJson>(table, HttpStatus.OK);
    }

    @RequestMapping(value = "/tree/{objectType}/{objectId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Set<Dictionary>> getTree(@PathVariable String objectType,
                                                   @PathVariable Long objectId) throws WrongTypeException {

        Set<Dictionary> result = dictionaryService.getTreeRecords(objectType, objectId);
        if (result == null) result = Collections.emptySet();

        return new ResponseEntity<Set<Dictionary>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/values/{objectType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Set<String>> getValuesForSelect(@PathVariable String objectType,
                                                          @RequestParam(required = false, name = ControlTerms.SEARCH_STRING) String searchString,
                                                          @RequestParam(required = false, name = ControlTerms.DATA_LIMIT) Integer maxSelect) throws WrongTypeException {

        List<Dictionary> result = dictionaryService.getRecords(
                objectType,
                0,
                maxSelect,
                Defenitions.FIELDS.NAME,"asc",
                searchString);

        return new ResponseEntity<Set<String>>(getOnlyNames(result),HttpStatus.OK);
    }

    @RequestMapping(value = "/{objectType}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createEntry(@PathVariable String objectType,
                                            @RequestParam Map<String,String> allRequestParams) throws WrongTypeException, ValidationException {

        Dictionary entity = loadEntryFromParams(dictionaryService.getNewEntry(objectType), allRequestParams);
        validateAndSaveEntity(entity);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{objectType}/{objectId}", method = {RequestMethod.POST, RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> saveEntry(@PathVariable String objectType,
                                          @PathVariable Long objectId,
                                          @RequestParam Map<String,String> allRequestParams) throws WrongTypeException, ValidationException {

        Dictionary entity = loadEntryFromParams(dictionaryService.getRecordById(objectType, objectId), allRequestParams);
        validateAndSaveEntity(entity);

        return new ResponseEntity<Void>(HttpStatus.OK);

    }

    @RequestMapping(value = "/{objectType}/{objectId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> deleteEntry(@PathVariable String objectType, @PathVariable Long objectId) throws WrongTypeException, DbOperationException {

        ApiTools.deleteEntry(dictionaryService, objectType, objectId);
        return new ResponseEntity<Void>(HttpStatus.OK);

    }

    @RequestMapping(value = "/tree/{objectType}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> saveTreeEntry(@PathVariable String objectType,
                                              @RequestParam Map<String,String> allRequestParams) throws WrongTypeException, ValidationException {

        TreeDictionary entry = loadTreeEntryFromParams(dictionaryService.getNewEntry(objectType), allRequestParams);
        validateAndSaveEntity(entry);

        return new ResponseEntity<Void>(HttpStatus.OK);

    }

    @RequestMapping(value = "/tree/{objectType}/{objectId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> saveTreeEntry(@PathVariable String objectType,
                                          @PathVariable Long objectId,
                                          @RequestParam Map<String,String> allRequestParams) throws WrongTypeException, ValidationException {

        TreeDictionary entry = loadTreeEntryFromParams(dictionaryService.getRecordById(objectType, objectId), allRequestParams);
        validateAndSaveEntity(entry);

        return new ResponseEntity<Void>(HttpStatus.OK);

    }

    private Dictionary loadEntryFromParams(Dictionary entry, Map<String,String> params){

        return  (Dictionary) ApiTools.buildEntryFromParams(entry, params);
    }

    private TreeDictionary loadTreeEntryFromParams(Dictionary entry, Map<String,String> params) throws WrongTypeException{

        TreeDictionary result = (TreeDictionary) ApiTools.buildEntryFromParams(entry, params);

        if (result != null){

            Long parentId = ApiTools.parseId(params.get(Defenitions.FIELDS.PARENT_ID));
            if (parentId == 0) {

                result.setParent(null);

            } else {

                TreeDictionary parent = (TreeDictionary) dictionaryService.getRecordById(result.getType(), parentId);
                result.setParent(parent);
            }
        }

        return result;
    }

    private Set<String> getOnlyNames(List<Dictionary> list){

        if (list == null) return Collections.EMPTY_SET;

        Set<String> result = new HashSet<String>(list.size());

        for (Dictionary element :  list) {
            result.add(element.getFullName());
        }

        return result;
    }

    private void validateAndSaveEntity(Dictionary entity) throws WrongTypeException, ValidationException {

        if (entity != null) {
            entity.setModifyBy(securityService.getCurrentUser());
        }

        ApiTools.validateEntry(entity);
        ApiTools.checkUniqueName(dictionaryService, entity);
        ApiTools.saveEntityToDb(dictionaryService, entity);
    }
}
