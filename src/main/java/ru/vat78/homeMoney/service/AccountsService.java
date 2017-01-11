package ru.vat78.homeMoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vat78.homeMoney.dao.accounts.AccountsDao;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.accounts.Account;
import ru.vat78.homeMoney.model.exceptions.WrongTypeException;

import java.util.*;

@Service
public class AccountsService extends CommonService<Account> {

    @Autowired
    AccountsDao accountsDao;

    @Override
    public List<Account> getRecords(String type, int offset, int size, String sortColumn, String sortOrder, String searchString) {
        return null;
    }

    @Override
    public Account getRecordByName(String accountType, String accountName) throws WrongTypeException {
        if (!isTypeExist(accountType)) throw new WrongTypeException(accountType);
        return accountsDao.findByName(accountType, accountName);
    }

    @Override
    public Account getRecordById(String type, Long id) throws WrongTypeException {

        if (!isTypeExist(type)) throw new WrongTypeException(type);
        return (Account) accountsDao.findById(type, id);
    }

    @Override
    public boolean isTypeExist(String type) {
        //ToDo: dog-nail
        return getAccountsTypes().contains(type);
    }

    @Override
    public boolean saveRecord(Account entity) {

        try {
            entity = (Account) accountsDao.save(entity);
        } catch (Exception ignored) {return false;}

        return entity != null;
    }

    @Override
    public boolean deleteRecord(Account entry) {
        return false;
    }

    @Override
    public boolean deleteRecordById(String accountType, Long id) throws WrongTypeException {

        if (!isTypeExist(accountType)) throw new WrongTypeException(accountType);
        try {
            accountsDao.deleteById(accountType, id);
        } catch (Exception ignored) {return false;}

        return true;
    }

    @Override
    public Account getNewEntry(String accountType){

        Account result = null;
        try {
            result = accountsDao.getNewEntity(accountType);
        } catch (Exception ignored) {}
        return result;
    }

    public List<Account> getAllAccounts(boolean active){
        return accountsDao.getAccountsByStatus(active);
    }

    public List<Account> getActiveAccountsByType(String accountType) throws WrongTypeException {
        if (!isTypeExist(accountType)) throw new WrongTypeException(accountType);
        return accountsDao.getAccountsByStatus(accountType, true);
    }

    public Set<String> getAccountsTypes(){
        Set<String> result = new HashSet<String>();
        result.add(Defenitions.TABLES.CASH_ACCOUNTS);
        result.add(Defenitions.TABLES.CREDIT_ACCOUNTS);
        return result;
    }

    public Account getRecordById(long id){
        return accountsDao.findById(id);
    }

}
