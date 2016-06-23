package ru.vat78.homeMoney.dao.dictionaries;


import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.dao.CommonEntryDao;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;

import java.util.List;

public abstract class DictionaryDao<T extends Dictionary> extends CommonEntryDao {

    @Transactional(readOnly = true)
    public T findByName(String name) {

        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq(Defenitions.FIELDS.NAME, name));
        return (T) criteria.uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<T> findAllByName(String name) {

        Criteria criteria = getCriteria();
        criteria.add(Restrictions.like(Defenitions.FIELDS.NAME, name, MatchMode.ANYWHERE))
                .addOrder(Order.asc(Defenitions.FIELDS.NAME));
        return (List<T>) criteria.list();

    }
}
