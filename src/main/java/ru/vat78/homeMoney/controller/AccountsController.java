package ru.vat78.homeMoney.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.User;
import ru.vat78.homeMoney.model.accounts.Account;
import ru.vat78.homeMoney.model.dictionaries.Currency;
import ru.vat78.homeMoney.model.tools.ColumnDefinition;
import ru.vat78.homeMoney.model.tools.UserTableSettings;
import ru.vat78.homeMoney.service.AccountsService;
import ru.vat78.homeMoney.service.DictionaryService;
import ru.vat78.homeMoney.service.SecurityService;
import ru.vat78.homeMoney.service.UserSettingsService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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

    @RequestMapping(value = "/data.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getTable(@RequestParam Map<String,String> allRequestParams){

        List<Account> list;
        String table = allRequestParams.get("type");
        if (table == null || table.length() == 0){
            table = Defenitions.TABLES.ACCOUNTS;
        }

        if (table.equals("closed")){
            list = accountsService.getAllAccounts(false);
        } else {
            list = accountsService.getActiveAccountsByType(table);
        }

        Gson gson = new GsonBuilder()
                .disableInnerClassSerialization()
                .serializeNulls()
                .registerTypeAdapter(User.class, GsonSerializerBuilder.getSerializer(User.class))
                .registerTypeAdapter(Currency.class, GsonSerializerBuilder.getSerializer(Currency.class))
                .setDateFormat(Defenitions.DATE_FORMAT)
                .create();
        return gson.toJson(list);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String saveEntry(@RequestParam Map<String,String> allRequestParams){

        Response res = new Response();

        Account entity = loadEntryFromParams(allRequestParams);

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

        checkUniqueName(entity, res);
        saveEntityToDb(entity, res);

        Gson gson = new GsonBuilder().create();
        return gson.toJson(res);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteEntry(@RequestParam Map<String,String> allRequestParams){
        Response result = new Response();
        result.setStatus("FAIL");

        String table = allRequestParams.get("table");
        String id = allRequestParams.get("id");
        if (table != null & id != null) {
            if (accountsService.deleteRecordById(table, Long.valueOf(id)))
                result.setStatus("SUCCESS");
        } else {
            result.setError("","There are no params 'id' and 'table'");
        }

        return new GsonBuilder().create().toJson(result);
    }

    private Account loadEntryFromParams(Map<String, String> params) {

        Account result;
        if (params.get(Defenitions.FIELDS.ID) == null || Long.parseLong(params.get(Defenitions.FIELDS.ID)) == 0) {
            result = accountsService.getNewEntry(params.get("table"));
        } else {
            result = accountsService.getAccountById(Long.parseLong(params.get(Defenitions.FIELDS.ID)));
        }

        WebDataBinder binder = new WebDataBinder(result);
        binder.bind(new MutablePropertyValues(params));

        result.setOpeningDate(params.get(Defenitions.FIELDS.OPENING_DATE),Defenitions.DATE_FORMAT);
        result.setCurrency((Currency) dictionaryService.getRecordByName(Defenitions.TABLES.CURRENCY, params.get(Defenitions.FIELDS.CURRENCY)));
        result.setModifyBy(securityService.getCurrentUser());

        return result;
    }

    private ModelAndView prepareAccountPage(String tableName, ModelAndView mv){

        UserTableSettings settings = userSettingsService.getTableSettings(securityService.getCurrentUser(),tableName);
        mv.addObject("tableDef", settings);

        TreeSet<ColumnDefinition> columns = new TreeSet<ColumnDefinition>();
        columns.addAll(settings.getColumns().values());
        mv.addObject("columns", columns);

        return mv;
    }

    private void checkUniqueName(Account entity, Response response){

        String accountType = entity.getType();
        if (response.getResult() == null) {
            Account inDB = accountsService.getRecordByName(accountType, entity.getName());
            if (inDB != null && inDB.getId() != entity.getId()){
                response.setError("name", "Such name already exists");
            }
        }
    }

    private void saveEntityToDb(Account entity, Response response) {

        String accountType = entity.getType();
        if (response.getResult() == null) {
            if (accountsService.saveRecord(entity)) {
                response.setStatus("SUCCESS");
                response.setResult(entity);
            } else {
                response.setError("name", "Can't save to database.");
            }
        }
    }


}
