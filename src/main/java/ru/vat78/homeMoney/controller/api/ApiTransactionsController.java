package ru.vat78.homeMoney.controller.api;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.vat78.homeMoney.controller.ControlTerms;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.User;
import ru.vat78.homeMoney.model.accounts.Account;
import ru.vat78.homeMoney.model.exceptions.ValidationException;
import ru.vat78.homeMoney.model.exceptions.WrongTypeException;
import ru.vat78.homeMoney.model.transactions.Transaction;
import ru.vat78.homeMoney.service.AccountsService;
import ru.vat78.homeMoney.service.SecurityService;
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
    SecurityService securityService;

    @Autowired
    MyGsonBuilder gsonBuilder;

    @RequestMapping(value = "/{objectType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Transaction>> getTable(@PathVariable String objectType,
                                                      @RequestParam Map<String,String> allRequestParams) throws WrongTypeException {

        long accountId = ApiTools.parseId(allRequestParams.get("account"));
        Account account = accountsService.getRecordById(accountId);

        List<Transaction> list = transactionsService.getTransactionsByAccount(
                account,
                Integer.valueOf(allRequestParams.get(ControlTerms.DATA_OFFSET)),
                Integer.valueOf(allRequestParams.get(ControlTerms.DATA_LIMIT)),
                allRequestParams.get(ControlTerms.SORT_COLUMN),allRequestParams.get(ControlTerms.SORT_ORDER),
                allRequestParams.get(ControlTerms.SEARCH_STRING)
        );

        return new ResponseEntity<List<Transaction>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/{objectType}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createEntry(@PathVariable String objectType,
                                            @RequestParam Map<String,String> allRequestParams) throws WrongTypeException, ValidationException {

        Transaction entity = loadEntryFromParams(transactionsService.getNewEntry(objectType), allRequestParams);
        validateAndSaveEntity(entity);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    private Transaction loadEntryFromParams(Transaction entry, Map<String, String> data) {

        Transaction result = (Transaction) ApiTools.buildEntryFromParams(entry, data);

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

    private void validateAndSaveEntity(Transaction entity) throws WrongTypeException, ValidationException {

        if (entity != null) {
            entity.setModifyBy(securityService.getCurrentUser());
        }

        ApiTools.validateEntry(entity);
        ApiTools.saveEntityToDb(transactionsService, entity);
    }
}
