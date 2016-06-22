package ru.vat78.homeMoney.dao;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.CreditAccount;
import ru.vat78.homeMoney.model.SimpleAccount;

@Repository("creditAccountsDao")
@Transactional
public class CreditAccountsDao extends SimpleAccountsDao {

    @Override
    protected Class<CreditAccount> getEntityClass() { return CreditAccount.class;}

}
