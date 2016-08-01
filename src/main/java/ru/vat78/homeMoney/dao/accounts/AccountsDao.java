package ru.vat78.homeMoney.dao.accounts;


import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.vat78.homeMoney.dao.dictionaries.DictionaryDao;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.accounts.SimpleAccount;

import java.util.List;

public abstract class AccountsDao extends DictionaryDao<SimpleAccount> {

    public List<SimpleAccount> getClosedAccounts(){
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq(Defenitions.FIELDS.ACTIVE, false));
        criteria.addOrder(Order.asc(Defenitions.FIELDS.NAME));
        return criteria.list();
    }
}
