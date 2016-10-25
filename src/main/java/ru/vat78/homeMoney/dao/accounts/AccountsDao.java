package ru.vat78.homeMoney.dao.accounts;


import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.dao.dictionaries.DictionaryDao;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.accounts.Account;

import java.util.List;

@Repository("accountsDao")
@Transactional
public class AccountsDao extends DictionaryDao<Account> {

    @Override
    public Class getEntityClass(String type) {
        return getEntityClass(Defenitions.GROUPS.ACCOUNTS, type);
    }

    @Override
    public Account getNewEntity(String type) throws java.lang.InstantiationException, IllegalAccessException {
        return (Account) getNewEntity(Defenitions.GROUPS.ACCOUNTS, type);
    }

    public List<Account> getAccountsByStatus(boolean active){
        Criteria criteria = getCriteria(getEntityClass(Defenitions.TABLES.ACCOUNTS));
        criteria.add(Restrictions.eq(Defenitions.FIELDS.ACTIVE, active));
        criteria.addOrder(Order.asc(Defenitions.FIELDS.NAME));
        return criteria.list();
    }

    public List<Account> getAccountsByStatus(String type, boolean active){
        Criteria criteria = getCriteria(getEntityClass(type));
        criteria.add(Restrictions.eq(Defenitions.FIELDS.ACTIVE, active));
        criteria.addOrder(Order.asc(Defenitions.FIELDS.NAME));
        return criteria.list();
    }

    public Account findById(Long id) {
        Account acc = (Account) findById(Defenitions.TABLES.ACCOUNTS, id);
        return (Account) findById(acc.getType(), id);
    }

}
