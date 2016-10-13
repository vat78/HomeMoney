package ru.vat78.homeMoney.dao.transactions;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.dao.CommonEntryDao;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.accounts.Account;
import ru.vat78.homeMoney.model.transactions.Transaction;

import java.util.Collections;
import java.util.List;

@Repository("transactionsDao")
@Transactional
public abstract class TransactionDao extends CommonEntryDao {

    @Override
    protected Class<? extends Transaction> getEntityClass() { return Transaction.class;}

    public List<Transaction> findAllForAccount(Account account) {

        if (account == null) return Collections.emptyList();

        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq(Defenitions.FIELDS.ACCOUNT_ID,account));

        return criteria.list();
    }

}
