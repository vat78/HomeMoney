package ru.vat78.homeMoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vat78.homeMoney.dao.accounts.AccountsDao;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.accounts.Account;

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
    public Account getRecordByName(String accountType, String accountName) {
        if (!isTypeExist(accountType)) return null;
        return accountsDao.findByName(accountType, accountName);
    }

    @Override
    public Account getRecordById(String dictionary, Long id) {
        return null;
    }

    @Override
    public boolean isTypeExist(String type) {
        return false;
    }

    @Override
    public boolean saveRecord(Account entity) {

        if (!isTypeExist(entity.getType())) return false;
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
    public boolean deleteRecordById(String accountType, Long id) {

        if (!isTypeExist(accountType)) return false;
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

    public List<Account> getActiveAccountsByType(String accountType){
        if (!isTypeExist(accountType)) return Collections.EMPTY_LIST;
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
