package ru.vat78.homeMoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vat78.homeMoney.dao.accounts.AccountsDao;
import ru.vat78.homeMoney.model.CommonEntry;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.accounts.Account;

import java.util.*;

@Service
public class AccountsService {

    @Autowired
    AccountsDao accountsDao;

    public List<Account> getAllAccounts(boolean active){
        return accountsDao.getAccountsByStatus(active);
    }

    public List<Account> getActiveAccountsByType(String accountType){
        if (!checkAccountType(accountType)) return Collections.EMPTY_LIST;
        return accountsDao.getAccountsByStatus(accountType, true);
    }

    public Set<String> getAccountsTypes(){
        Set<String> result = new HashSet<String>();
        result.add(Defenitions.TABLES.CASH_ACCOUNTS);
        result.add(Defenitions.TABLES.CREDIT_ACCOUNTS);
        return result;
    }

    public Account getNewEntry(String accountType){

        Account result = null;
        try {
            result = accountsDao.getNewEntity(accountType);
        } catch (Exception ignored) {}
        return result;

    }

    public Account getAccountById(long id){
        return accountsDao.findById(id);
    }

    public Account getRecordByName(String accountType, String accountName) {
        if (!checkAccountType(accountType)) return null;
        return accountsDao.findByName(accountType, accountName);
    }

    public boolean saveRecord(Account entity) {

        if (!checkAccountType(entity.getType())) return false;
        try {
            entity = (Account) accountsDao.save(entity);
        } catch (Exception ignored) {return false;}

        return entity != null;
    }

    public boolean checkAccountType(String accountType) {
        return (getNewEntry(accountType) != null);
    }

    public boolean deleteRecordById(String accountType, Long id) {

        if (!checkAccountType(accountType)) return false;
        try {
            accountsDao.deleteById(accountType, id);
        } catch (Exception ignored) {return false;}

        return true;
    }
}
