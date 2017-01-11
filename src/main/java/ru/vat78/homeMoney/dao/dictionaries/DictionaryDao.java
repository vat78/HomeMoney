package ru.vat78.homeMoney.dao.dictionaries;


import com.sun.istack.internal.NotNull;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.dao.CommonEntryDao;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;
import ru.vat78.homeMoney.model.dictionaries.TreeDictionary;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Repository("dictionaryDao")
public class DictionaryDao<T extends Dictionary> extends CommonEntryDao {

    public Class<T> getEntityClass(String type) {
        return getEntityClass(Defenitions.GROUPS.DICTIONARIES, type);
    }

    @Override
    public T getNewEntity(String type) throws java.lang.InstantiationException, IllegalAccessException {
        return (T) getNewEntity(Defenitions.GROUPS.DICTIONARIES, type);
    }

    @Transactional(readOnly = true)
    public T findByName(String type, String name) {

        Criteria criteria = getCriteria(getEntityClass(type));
        criteria.add(Restrictions.eq(Defenitions.FIELDS.SEARCH_NAME, name.toLowerCase()));
        return (T) criteria.uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<T> findAllByName(String type, String name) {

        Criteria criteria = getCriteria(getEntityClass(type));
        criteria.add(Restrictions.like(Defenitions.FIELDS.SEARCH_NAME, name.toLowerCase(), MatchMode.ANYWHERE))
                .addOrder(Order.asc(Defenitions.FIELDS.NAME));
        return (List<T>) criteria.list();

    }

    protected Criteria getCriteriaWithSearching(String type, String searchString){
        Criteria criteria = getCriteria(getEntityClass(type));
        if (searchString != null && searchString.length()>1){
            criteria.add(Restrictions.like(Defenitions.FIELDS.SEARCH_NAME,searchString.toLowerCase(), MatchMode.ANYWHERE));
            criteria.addOrder(Order.asc(Defenitions.FIELDS.NAME));
        }
        return criteria;
    }


    @Transactional(readOnly = true)
    public Set<? extends TreeDictionary> getAllChildren(@NotNull Class type, TreeDictionary parent) {

        Criteria criteria = getCriteria(type);

        if (parent == null || parent.getId() == 0) {
            criteria.add(Restrictions.isNull(Defenitions.FIELDS.PARENT_ID));
        } else {
            return parent.getChildren();
            //criteria.add(Restrictions.eq(Defenitions.FIELDS.PARENT_ID, parent));
        }

        criteria.addOrder(Order.asc(Defenitions.FIELDS.NAME));

        TreeSet<TreeDictionary> result = new TreeSet<TreeDictionary>(criteria.list());
        return result;
    }

    @Transactional(readOnly = true)
    public Set<? extends TreeDictionary> getAllChildrenById(String type, long id) {

        return getAllChildren(getEntityClass(type), (TreeDictionary) findById(type, id));
    }

    @Transactional(readOnly = false)
    public void deleteAllChildren(@NotNull TreeDictionary parent) {

        Set<? extends TreeDictionary> children = getAllChildren(parent.getClass(), parent);
        for (TreeDictionary child: children){
            delete(child);
        }
    }
}
