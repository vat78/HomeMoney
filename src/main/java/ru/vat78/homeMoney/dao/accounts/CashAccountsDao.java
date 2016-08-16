package ru.vat78.homeMoney.dao.accounts;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.accounts.CashAccount;

@Repository("accountsDao")
@Transactional
public class CashAccountsDao extends AccountsDao {

    @Override
    protected Class<CashAccount> getEntityClass() { return CashAccount.class;}

    @Override
    public CashAccount getNewEntity() {
        CashAccount res = new CashAccount();
        res.setAccountType(Defenitions.ACCOUNTS_TYPE.CASH);
        return res;
    }
}
