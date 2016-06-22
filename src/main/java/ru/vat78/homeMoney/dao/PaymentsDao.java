package ru.vat78.homeMoney.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.Payment;

@Repository("paymentsDao")
@Transactional
public class PaymentsDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public Payment findById(long id) {
        return getSession().get(Payment.class, id);
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
        return getSession().createCriteria(Payment.class);
    }

}
