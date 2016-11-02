package ru.vat78.homeMoney.controller.api;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vat78.homeMoney.controller.ControlTerms;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.User;
import ru.vat78.homeMoney.model.accounts.Account;
import ru.vat78.homeMoney.model.transactions.Transaction;
import ru.vat78.homeMoney.service.AccountsService;
import ru.vat78.homeMoney.service.TransactionsService;

import java.util.List;
import java.util.Map;

@Controller
@Secured({"ROLE_USER","ROLE_ADMIN"})
@RequestMapping(ControlTerms.API_TRANSACTIONS)
public class ApiTransactionsController {

    @Autowired
    TransactionsService transactionsService;

    @Autowired
    AccountsService accountsService;

    @Autowired
    MyGsonBuilder gsonBuilder;

    @RequestMapping(value = ControlTerms.API_TABLE_DATA, method = RequestMethod.GET, produces = ControlTerms.API_FORMAT)
    @ResponseBody
    public String getTable(@RequestParam Map<String,String> allRequestParams){

        long accountId = ApiTools.parseId(allRequestParams.get("account"));
        Account account = accountsService.getRecordById(accountId);

        List<Transaction> list;
        list = transactionsService.getTransactionsByAccount(
                account,
                Integer.valueOf(allRequestParams.get(ControlTerms.DATA_OFFSET)),
                Integer.valueOf(allRequestParams.get(ControlTerms.DATA_LIMIT)),
                allRequestParams.get(ControlTerms.SORT_COLUMN),allRequestParams.get(ControlTerms.SORT_ORDER),
                allRequestParams.get(ControlTerms.SEARCH_STRING)
        );

        Gson gson = gsonBuilder.getGsonBuilder()
                .disableInnerClassSerialization()
                .serializeNulls()
                .registerTypeAdapter(User.class, GsonSerializerBuilder.getSerializer(User.class))
                .setDateFormat(Defenitions.DATE_FORMAT)
                .create();
        return gson.toJson(list);
    }

    @RequestMapping(value = ControlTerms.SAVE, method = RequestMethod.POST, produces = ControlTerms.API_FORMAT)
    @ResponseBody
    public String saveEntry(@RequestParam Map<String,String> allRequestParams){

        Response res = new Response();

        Transaction entity = loadEntryFromParams(allRequestParams, res);

        ApiTools.validateEntry(entity, res);
        ApiTools.saveEntityToDb(accountsService, entity, res);

        Gson gson = gsonBuilder.getGsonBuilder().create();
        return gson.toJson(res);
    }

    private Transaction loadEntryFromParams(Map<String, String> data, Response response) {

        Transaction result = (Transaction) ApiTools.loadEntryFromDb(transactionsService, data.get(Defenitions.FIELDS.OPERATION_TYPE), data.get(Defenitions.FIELDS.ID));

        /*
        if (result.getSum() == 0) {
            response.setError(Defenitions.FIELDS.SUM, "Please, enter the sum of transaction");
        }

        if (!result.setDate(data.get(Defenitions.FIELDS.DATE),Defenitions.DATE_FORMAT)){
            response.setError(Defenitions.FIELDS.DATE, "Wrong date of transaction");
        }

        result.setAccount(accountsService.getRecordById(strToLong(data.get(Defenitions.FIELDS.ACCOUNT_ID))));
        if (result.getAccount() == null) {
            response.setError(Defenitions.FIELDS.ACCOUNT_ID, "Please, select account");
        }

        if (data.get(Defenitions.FIELDS.OPERATION_TYPE).equals(Defenitions.TABLES.TRANSFERS)){
            Transfer transfer = (Transfer) result;
            transfer.setCorrAccount(accountsService.getRecordById(strToLong(data.get(Defenitions.FIELDS.CORRESPONDING_ACCOUNT))));
            if (transfer.getCorrAccount() == null || transfer.getCorrAccount().getId() == transfer.getAccount().getId()) {
                response.setError(Defenitions.FIELDS.CORRESPONDING_ACCOUNT, "Wrong corresponding account");
            }
        }
        */

        return result;
    }
}
