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
import ru.vat78.homeMoney.model.accounts.Account;
import ru.vat78.homeMoney.model.dictionaries.Currency;
import ru.vat78.homeMoney.service.AccountsService;
import ru.vat78.homeMoney.service.DictionaryService;
import ru.vat78.homeMoney.service.SecurityService;

import java.util.List;
import java.util.Map;

@Controller
@Secured({"ROLE_USER","ROLE_ADMIN"})
@RequestMapping("/api/accounts")
public class ApiAccountsController {

    @Autowired
    AccountsService accountsService;

    @Autowired
    DictionaryService dictionaryService;

    @Autowired
    SecurityService securityService;

    @Autowired
    MyGsonBuilder gsonBuilder;

    @RequestMapping(value = "/data.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getTable(@RequestParam Map<String,String> allRequestParams){

        List<Account> list;
        String table = allRequestParams.get(Defenitions.FIELDS.TABLE);
        if (table == null || table.length() == 0){
            table = Defenitions.TABLES.ACCOUNTS;
        }

        if (table.equals(Defenitions.TABLES.ACCOUNTS) || table.equals("closed")){
            list = accountsService.getAllAccounts(table.equals(Defenitions.TABLES.ACCOUNTS));
        } else {
            list = accountsService.getActiveAccountsByType(table);
        }

        Gson gson = gsonBuilder.getGsonBuilder()
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

        Account entity = loadEntryFromParams(allRequestParams, res);

        ApiTools.validateEntry(entity, res);
        ApiTools.checkUniqueName(accountsService, entity, res);
        ApiTools.saveEntityToDb(accountsService, entity, res);

        Gson gson = gsonBuilder.getGsonBuilder().create();
        return gson.toJson(res);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteEntry(@RequestParam Map<String,String> allRequestParams){

        Response result = new Response();
        ApiTools.deleteEntry(accountsService, allRequestParams.get("table"), allRequestParams.get("id"), result);

        return gsonBuilder.getGsonBuilder().create().toJson(result);
    }

    private Account loadEntryFromParams(Map<String, String> params, Response response) {

        Account result = (Account) ApiTools.buildEntryFromParams(accountsService, params, response);

        if (result != null) {
            result.setModifyBy(securityService.getCurrentUser());
            result.setOpeningDate(params.get(Defenitions.FIELDS.OPENING_DATE), Defenitions.DATE_FORMAT);
            result.setCurrency((Currency) dictionaryService.getRecordByName(Defenitions.TABLES.CURRENCY, params.get(Defenitions.FIELDS.CURRENCY)));
        }

        return result;
    }

}