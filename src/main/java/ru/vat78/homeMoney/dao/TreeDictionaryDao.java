package ru.vat78.homeMoney.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.TreeDictionary;

import java.util.List;

public abstract class TreeDictionaryDao<T extends TreeDictionary> extends DictionaryDao {

    @Transactional(readOnly = true)
    public List<T> getAllChildren(T parent) {

        Criteria criteria = getCriteria();

        if (parent == null) {
            criteria.add(Restrictions.isNull("parent"));
        } else {
            criteria.add(Restrictions.eq("parent", parent));
        }

        criteria.addOrder(Order.asc("name"));
        return (List<T>) criteria.list();
    }

    @Transactional(readOnly = false)
    public void deleteAllChildren(T parent) {

        List<T> children = getAllChildren(parent);
        for (TreeDictionary child: children){
            delete(child);
        }
    }
}
