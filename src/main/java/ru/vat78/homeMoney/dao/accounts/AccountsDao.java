package ru.vat78.homeMoney.dao.accounts;


import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.dao.dictionaries.DictionaryDao;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.accounts.Account;

import java.util.List;

@Transactional
public abstract class AccountsDao extends DictionaryDao<Account> {

    public List<Account> getAccountsByStatus(boolean active){
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq(Defenitions.FIELDS.ACTIVE, active));
        criteria.addOrder(Order.asc(Defenitions.FIELDS.NAME));
        return criteria.list();
    }

}
