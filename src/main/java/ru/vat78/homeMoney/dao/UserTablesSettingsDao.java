package ru.vat78.homeMoney.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.User;
import ru.vat78.homeMoney.model.tools.UserTableSettings;

@Repository("tablesSettingsDao")
@Transactional
public class UserTablesSettingsDao {

    @Autowired
    private SessionFactory sessionFactory;

    public UserTableSettings getTableSettings(User user, String tableName){

        Criteria criteria = getCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq(Defenitions.FIELDS.USER, user),
                Restrictions.eq(Defenitions.FIELDS.NAME, tableName)
        ));
        return (UserTableSettings) criteria.uniqueResult();
    }

    public void save(UserTableSettings entity){
        getSession().merge(entity);
    }


    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    protected Criteria getCriteria(){
        return getSession().createCriteria(UserTableSettings.class);
    }
}
