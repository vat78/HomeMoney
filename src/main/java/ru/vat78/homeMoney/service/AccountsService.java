package ru.vat78.homeMoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vat78.homeMoney.dao.accounts.AccountsDao;
import ru.vat78.homeMoney.dao.accounts.AccountsDaoFactory;
import ru.vat78.homeMoney.model.accounts.Account;

import java.util.*;

@Service
public class AccountsService {

    @Autowired
    AccountsDaoFactory daoFactory;

    public List<Account> getAllAccounts(boolean active){

        List<Account> result = new ArrayList<Account>();

        for (AccountsDao source: daoFactory.getAllDao()){
            List<Account> list = source.getAccountsByStatus(active);
            result.addAll(list);
        }
        result.sort(null);
        return result;
    }

    public List<Account> getActiveAccountsByType(String accountType){
        if (!checkAccountType(accountType)) return Collections.EMPTY_LIST;
        return daoFactory.getDao(accountType).getAccountsByStatus(true);
    }

    public Set<String> getAccountsTypes(){
        return daoFactory.getAllTypes();
    }

    public Account getNewEntry(String accountType){
        if (!checkAccountType(accountType)) return null;
        return (Account) daoFactory.getDao(accountType).getNewEntity();
    }

    public Account getAccountById(long id){
        Account result = null;
        for (AccountsDao source: daoFactory.getAllDao()){
            result = (Account) source.findById(id);
            if (result != null) break;
        }
        return result;
    }

    public Account getRecordByName(String accountType, String accountName) {
        if (!checkAccountType(accountType)) return null;
        return daoFactory.getDao(accountType).findByName(accountName);
    }

    public boolean saveRecord(Account entity) {

        if (!checkAccountType(entity.getType())) return false;
        try {
            entity = (Account) daoFactory.getDao(entity.getType()).save(entity);
        } catch (Exception ignored) {return false;}

        return entity != null;
    }

    public boolean checkAccountType(String accountType) {
        return (daoFactory.getDao(accountType) != null);
    }

    public boolean deleteRecordById(String accountType, Long id) {

        if (!checkAccountType(accountType)) return false;
        try {
            daoFactory.getDao(accountType).deleteById(id);
        } catch (Exception ignored) {return false;}

        return true;
    }
}
