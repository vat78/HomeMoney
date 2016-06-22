package ru.vat78.homeMoney.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.SimpleAccount;
import ru.vat78.homeMoney.model.Transaction;

import java.util.Collections;
import java.util.List;

@Repository("transactionsDao")
@Transactional
public class TransactionDao extends CommonEntryDao {

    protected Class<? extends Transaction> getEntityClass() { return Transaction.class;}

    public List<Transaction> findAllForAccount(SimpleAccount account) {

        if (account == null) return Collections.emptyList();

        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("account",account));

        return criteria.list();
    }
}
