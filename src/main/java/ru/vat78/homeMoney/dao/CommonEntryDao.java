package ru.vat78.homeMoney.dao;


import com.sun.istack.internal.NotNull;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.CommonEntry;
import ru.vat78.homeMoney.model.Defenitions;

import java.util.Date;
import java.util.List;

public abstract class CommonEntryDao<T extends CommonEntry> {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public T findById(String type, long id) {
        return (T) getSession().get(getEntityClass(type), id);
    }

    @Transactional(readOnly = false)
    public T save(@NotNull T entity){
        return (T) getSession().merge(fillProtocolFields(entity));
    }

    @Transactional(readOnly = false)
    public void add(@NotNull T entity){
        getSession().persist(fillProtocolFields(entity));
    }

    @Transactional(readOnly = true)
    public List<T> getAll(String type){
        return getCriteria(getEntityClass(type)).list();
    }

    @Transactional(readOnly = true)
    public List<T> getPart(String type, int offset, int size, String sortColumn, String sortOrder, String searchString){

        Criteria criteria;
        if (searchString == null || searchString.length()<3) {
            criteria = getCriteria(getEntityClass(type));
        } else {
            criteria = getCriteriaWithSearching(getEntityClass(type), searchString);
        }
        if (sortOrder.equals("desc")) {
            criteria.addOrder(Order.desc(sortColumn));
        } else {
            criteria.addOrder(Order.asc(sortColumn));
        }
        criteria.setFirstResult(offset);
        criteria.setMaxResults(size);
        return criteria.list();
    }

    @Transactional
    public void delete(@NotNull T entity){
        getSession().delete(entity);
    }

    @Transactional
    public void deleteById(String type, long id){
        CommonEntry entry = findById(type, id);
        if (entry != null) getSession().delete(entry);
    }

    @Transactional(readOnly = true)
    public long getCount(String type) {
        Criteria criteria = getCriteria(getEntityClass(type));
        Long cnt = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        if (cnt == null) cnt = 0L;
        return cnt;
    }

    public Class<T> getEntityClass(String group, String object){

        StringBuilder sb = new StringBuilder("ru.vat78.homeMoney.model.");
        if (group != null && group.length() > 0) sb.append(group).append(".");
        sb.append(object);

        Class c = CommonEntry.class;
        try {
            c = Class.forName(sb.toString());
        } catch (ClassNotFoundException ignored) {}

        return c;
    }

    public T getNewEntity(String group, String object) throws java.lang.InstantiationException, IllegalAccessException {
        return getEntityClass(group, object).newInstance();
    }

//    protected abstract Class<T> getEntityClass();

//    public abstract T getNewEntity();

    public abstract Class<T> getEntityClass(String type);

    public abstract T getNewEntity(String type) throws java.lang.InstantiationException, IllegalAccessException ;

    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    protected Criteria getCriteria(Class type){
        return getSession().createCriteria(type);
    }

    protected Criteria getCriteriaWithSearching(Class type, String searchString){
        Criteria res = getCriteria(type);
        res.add(Restrictions.like(Defenitions.FIELDS.SEARCH_NAME, searchString.toLowerCase()));
        return res;
    }

    private T fillProtocolFields(T entity){

        Date timeStamp = new Date(System.currentTimeMillis());
        Date created = entity.getCreateOn();
        if (created == null || created.getTime() == 0) {
            entity.setCreateOn(timeStamp);
            entity.setCreateBy(entity.getModifyBy());
        }
        entity.setModifyOn(timeStamp);
        return entity;
    }
}
