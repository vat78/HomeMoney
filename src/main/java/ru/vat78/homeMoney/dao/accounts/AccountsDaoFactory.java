package ru.vat78.homeMoney.dao.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vat78.homeMoney.model.Defenitions;

import java.util.*;

@Component
public class AccountsDaoFactory {

    private Map<String, AccountsDao> dbEngines;

    @Autowired
    CreditAccountsDao creditDao;

    @Autowired
    CashAccountsDao cashAccountsDao;

    public AccountsDao getDao(String accountType){
        if (dbEngines == null) fillMap();
        return dbEngines.get(accountType);
    }

    public Collection<AccountsDao> getAllDao(){
        if (dbEngines == null) fillMap();
        return dbEngines.values();
    }

    public Set<String> getAllTypes(){
        if (dbEngines == null) fillMap();
        return dbEngines.keySet();
    }

    private void fillMap() {
        dbEngines = new HashMap<String, AccountsDao>();
        dbEngines.put(Defenitions.TABLES.ACCOUNTS, cashAccountsDao);
        dbEngines.put(Defenitions.TABLES.CREDIT_ACCOUNTS,creditDao);
    }
}
