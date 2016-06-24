package ru.vat78.homeMoney.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.Payment;
import ru.vat78.homeMoney.model.User;

import java.util.List;

@Repository("usersDao")
@Transactional
public class UsersDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public User findById(long id) {
        return getSession().get(User.class, id);
    }

    @Transactional(readOnly = false)
    public User save(User entity){
        return (User) getSession().merge(entity);
    }

    @Transactional(readOnly = true)
    public User findByName(String name) {

        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq(Defenitions.FIELDS.NAME, name));
        return (User) criteria.uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<User> getAll(){
        return getCriteria().list();
    }

    @Transactional(readOnly = true)
    public List<User> getAllAdmins(){
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq(Defenitions.FIELDS.IS_ADMIN,true));
        return criteria.list();
    }

    @Transactional
    public void delete(User entity){
        getSession().delete(entity);
    }

    @Transactional(readOnly = true)
    public long getCount() {
        Criteria criteria = getCriteria();
        Long cnt = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        if (cnt == null) cnt = 0L;
        return cnt;
    }

    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    protected Criteria getCriteria(){
        return getSession().createCriteria(User.class);
    }

}
