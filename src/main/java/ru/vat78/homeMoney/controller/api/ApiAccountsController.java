package ru.vat78.homeMoney.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.vat78.homeMoney.controller.ControlTerms;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.accounts.Account;
import ru.vat78.homeMoney.model.dictionaries.Currency;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;
import ru.vat78.homeMoney.model.exceptions.DbOperationException;
import ru.vat78.homeMoney.model.exceptions.ValidationException;
import ru.vat78.homeMoney.model.exceptions.WrongTypeException;
import ru.vat78.homeMoney.service.AccountsService;
import ru.vat78.homeMoney.service.DictionaryService;
import ru.vat78.homeMoney.service.SecurityService;

import java.util.List;
import java.util.Map;

@Controller
@Secured({"ROLE_USER","ROLE_ADMIN"})
@RequestMapping(ControlTerms.API_ACCOUNTS)
public class ApiAccountsController {

    @Autowired
    AccountsService accountsService;

    @Autowired
    DictionaryService dictionaryService;

    @Autowired
    SecurityService securityService;

    @Autowired
    MyGsonBuilder gsonBuilder;

    @RequestMapping(value = "/{objectType}/{objectId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Account> getElement(@PathVariable String objectType, @PathVariable Long objectId) throws WrongTypeException {

        Account result = accountsService.getRecordById(objectType, objectId);
        //ToDo: is it right to make emty object?
        if (result == null) result = accountsService.getNewEntry(objectType);

        return new ResponseEntity<Account>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{objectType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TableForJson> getTable(@PathVariable String objectType) throws WrongTypeException {

        List<Account> list;

        if (objectType == null || objectType.length() == 0){
            objectType = Defenitions.TABLES.ACCOUNTS;
        }

        if (objectType.equals(Defenitions.TABLES.ACCOUNTS) || objectType.equals("closed")){
            list = accountsService.getAllAccounts(objectType.equals(Defenitions.TABLES.ACCOUNTS));
        } else {
            list = accountsService.getActiveAccountsByType(objectType);
        }

        //ToDo: do I need this additional conversion?
        TableForJson result = new TableForJson();
        result.setTotal(list.size());
        result.setRows(list);

        return new ResponseEntity<TableForJson>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{objectType}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createEntry(@PathVariable String objectType, @RequestParam Map<String,String> allRequestParams) throws WrongTypeException, ValidationException {

        buildAndSaveEntity(accountsService.getNewEntry(objectType), allRequestParams);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{objectType}/{objectId}", method = {RequestMethod.POST, RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> saveEntry(@PathVariable String objectType, @PathVariable Long objectId, @RequestParam Map<String,String> allRequestParams) throws WrongTypeException, ValidationException {

        buildAndSaveEntity(accountsService.getRecordById(objectId), allRequestParams);
        return new ResponseEntity<Void>(HttpStatus.OK);

    }

    @RequestMapping(value = "/{objectType}/{objectId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> deleteEntry(@PathVariable String objectType, @PathVariable Long objectId) throws WrongTypeException, DbOperationException {

        ApiTools.deleteEntry(accountsService, objectType, objectId);
        return new ResponseEntity<Void>(HttpStatus.OK);

    }

    private void buildAndSaveEntity(Account entity, Map<String, String> params) throws WrongTypeException, ValidationException {

        Account result = (Account) ApiTools.buildEntryFromParams(entity, params);

        if (result != null) {
            result.setModifyBy(securityService.getCurrentUser());
            result.setOpeningDate(params.get(Defenitions.FIELDS.OPENING_DATE), Defenitions.DATE_FORMAT);
            result.setCurrency((Currency) dictionaryService.getRecordByName(Defenitions.TABLES.CURRENCY, params.get(Defenitions.FIELDS.CURRENCY)));
        }

        ApiTools.validateEntry(result);
        ApiTools.checkUniqueName(accountsService, result);
        ApiTools.saveEntityToDb(accountsService, result);
    }

}
