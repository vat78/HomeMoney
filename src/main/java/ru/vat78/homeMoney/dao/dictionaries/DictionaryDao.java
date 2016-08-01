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
import java.util.Map;

public abstract class DictionaryDao<T extends Dictionary> extends CommonEntryDao {

    @Transactional(readOnly = true)
    public T findByName(String name) {

        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq(Defenitions.FIELDS.SEARCH_NAME, name.toLowerCase()));
        return (T) criteria.uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<T> findAllByName(String name) {

        Criteria criteria = getCriteria();
        criteria.add(Restrictions.like(Defenitions.FIELDS.SEARCH_NAME, name.toLowerCase(), MatchMode.ANYWHERE))
                .addOrder(Order.asc(Defenitions.FIELDS.NAME));
        return (List<T>) criteria.list();

    }

    @Override
    protected Criteria getCriteriaWithSearching(String searchString){
        Criteria criteria = getCriteria();
        if (searchString != null && searchString.length()>1){
            criteria.add(Restrictions.like(Defenitions.FIELDS.SEARCH_NAME,searchString.toLowerCase(), MatchMode.ANYWHERE));
            criteria.addOrder(Order.asc(Defenitions.FIELDS.NAME));
        }
        return criteria;
    }
}
