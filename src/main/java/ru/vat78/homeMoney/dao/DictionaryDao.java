package ru.vat78.homeMoney.dao;


import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.Dictionary;

import java.util.List;

public abstract class DictionaryDao<T extends Dictionary> extends CommonEntryDao {

    @Transactional(readOnly = true)
    public T findByName(String name) {

        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("name", name));
        return (T) criteria.uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<T> findAllByName(String name) {

        Criteria criteria = getCriteria();
        criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE))
                .addOrder(Order.asc("name"));
        return (List<T>) criteria.list();

    }
}
