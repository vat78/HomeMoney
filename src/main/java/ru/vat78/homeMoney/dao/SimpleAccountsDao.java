package ru.vat78.homeMoney.dao;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.SimpleAccount;

@Repository("accountsDao")
@Transactional
public class SimpleAccountsDao extends DictionaryDao<SimpleAccount> {

    protected Class<SimpleAccount> getEntityClass() { return SimpleAccount.class;}

}
