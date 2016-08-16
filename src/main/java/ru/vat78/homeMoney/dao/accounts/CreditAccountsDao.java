package ru.vat78.homeMoney.dao.accounts;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.accounts.CreditAccount;

@Repository("creditAccountsDao")
@Transactional
public class CreditAccountsDao extends AccountsDao {

    @Override
    protected Class<CreditAccount> getEntityClass() { return CreditAccount.class;}

    @Override
    public CreditAccount getNewEntity() {
        CreditAccount res = new CreditAccount();
        res.setAccountType(Defenitions.ACCOUNTS_TYPE.CREDIT);
        return res;
    }
}
