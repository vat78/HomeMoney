package ru.vat78.homeMoney.dao.accounts;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.dao.dictionaries.DictionaryDao;
import ru.vat78.homeMoney.model.accounts.SimpleAccount;
import ru.vat78.homeMoney.service.SimpleDictionaryService;

@Repository("accountsDao")
@Transactional
public class SimpleAccountsDao extends DictionaryDao<SimpleAccount> {

    @Override
    protected Class<? extends SimpleAccount> getEntityClass() { return SimpleAccount.class;}

    @Override
    public SimpleAccount getNewEntity() { return new SimpleAccount();}
}