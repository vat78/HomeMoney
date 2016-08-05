package ru.vat78.homeMoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vat78.homeMoney.dao.accounts.AccountsDao;
import ru.vat78.homeMoney.dao.accounts.AccountsDaoFactory;
import ru.vat78.homeMoney.model.accounts.SimpleAccount;

import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Set;

@Service
public class AccountsService {

    @Autowired
    AccountsDaoFactory daoFactory;

    public List<SimpleAccount> getClosedAccounts(){

        List<SimpleAccount> result = Collections.emptyList();

        for (AccountsDao source: daoFactory.getAllDao()){
            List<SimpleAccount> list = source.getClosedAccounts();
            result.addAll(list);
        }
        return result;

    }

    public List<SimpleAccount> getAccountsByType(String accountType){
        if (!checkAccountType(accountType)) return Collections.EMPTY_LIST;
        return daoFactory.getDao(accountType).getAll();
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
