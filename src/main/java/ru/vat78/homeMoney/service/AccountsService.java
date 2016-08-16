package ru.vat78.homeMoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vat78.homeMoney.dao.accounts.AccountsDao;
import ru.vat78.homeMoney.dao.accounts.AccountsDaoFactory;
import ru.vat78.homeMoney.model.accounts.SimpleAccount;

import java.util.*;

@Service
public class AccountsService {

    @Autowired
    AccountsDaoFactory daoFactory;

    public List<SimpleAccount> getClosedAccounts(){

        List<SimpleAccount> result = new ArrayList<SimpleAccount>();

        for (AccountsDao source: daoFactory.getAllDao()){
            List<SimpleAccount> list = source.getAccountsByStatus(false);
            result.addAll(list);
        }
        return result;

    }

    public List<SimpleAccount> getActiveAccountsByType(String accountType){
        if (!checkAccountType(accountType)) return Collections.EMPTY_LIST;
        return daoFactory.getDao(accountType).getAccountsByStatus(true);
    }

    public Set<String> getAccountsTypes(){
        return daoFactory.getAllTypes();
    }

    public SimpleAccount getNewEntry(String accountType){
        if (!checkAccountType(accountType)) return null;
        return (SimpleAccount) daoFactory.getDao(accountType).getNewEntity();
    }

    public SimpleAccount getRecordByName(String accountType, String accountName) {
        if (!checkAccountType(accountType)) return null;
        return daoFactory.getDao(accountType).findByName(accountName);
    }

    public boolean saveRecord(String accountType, SimpleAccount entity) {

        if (!checkAccountType(accountType)) return false;
        try {
            entity = (SimpleAccount) daoFactory.getDao(accountType).save(entity);
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
