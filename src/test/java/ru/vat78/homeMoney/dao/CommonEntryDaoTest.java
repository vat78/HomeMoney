package ru.vat78.homeMoney.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import ru.vat78.homeMoney.config.HibernateConfiguration;
import ru.vat78.homeMoney.config.HibernateConfigurationTest;
import ru.vat78.homeMoney.model.Dictionary;

import java.util.List;

import static org.testng.Assert.*;


@ContextConfiguration(classes = { HibernateConfiguration.class })
public class CommonEntryDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    protected void makeDictionaryTest(DictionaryDao dao, Dictionary element) {

        long cnt = dao.getCount();
        Dictionary template = element;

        dao.save(template);
        assertEquals(dao.getCount(),cnt+1);

        Dictionary result = dao.findByName(template.getName());
        assertEquals(result, template);

        result = (Dictionary) dao.findById(result.getId());
        assertEquals(result, template);

        List<Dictionary> list = dao.findAllByName("test");
        assertTrue(list.contains(result));

        dao.delete(result);
        assertEquals(dao.getCount(),cnt);
    }
}