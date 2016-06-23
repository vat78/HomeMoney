package ru.vat78.homeMoney.dao.dictionaries;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.dictionaries.TreeDictionary;

import java.util.List;

public abstract class TreeDictionaryDao<T extends TreeDictionary> extends DictionaryDao {

    @Transactional(readOnly = true)
    public List<T> getAllChildren(T parent) {

        Criteria criteria = getCriteria();

        if (parent == null) {
            criteria.add(Restrictions.isNull(Defenitions.FIELDS.PARENT_ID));
        } else {
            criteria.add(Restrictions.eq(Defenitions.FIELDS.PARENT_ID, parent));
        }

        criteria.addOrder(Order.asc(Defenitions.FIELDS.NAME));
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
