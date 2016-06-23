package ru.vat78.homeMoney.dao;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.CommonEntry;

import java.util.Date;
import java.util.List;

public abstract class CommonEntryDao<T extends CommonEntry> {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public T findById(long id) {
        return getSession().get(getEntityClass(), id);
    }

    @Transactional(readOnly = false)
    public T save(T entity){

        return (T) getSession().merge(fillProtocolFields(entity));

    }

    @Transactional(readOnly = false)
    public void add(T entity){
        getSession().persist(fillProtocolFields(entity));
    }

    @Transactional(readOnly = true)
    public List<T> getAll(){
        return getCriteria().list();
    }

    @Transactional
    public void delete(T entity){
        getSession().delete(entity);
    }

    @Transactional(readOnly = true)
    public long getCount() {
        Criteria criteria = getCriteria();
        Long cnt = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        if (cnt == null) cnt = 0L;
        return cnt;
    }

    protected abstract Class<T> getEntityClass();

    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    protected Criteria getCriteria(){
        return getSession().createCriteria(getEntityClass());
    }

    private T fillProtocolFields(T entity){

        Date timeStamp = new Date(System.currentTimeMillis());
        Date created = entity.getCreateOn();
        if (created == null || created.getTime() == 0) {
            entity.setCreateOn(timeStamp);
        }
        entity.setModifyOn(timeStamp);
        return entity;
    }
}
