package ru.vat78.homeMoney.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import ru.vat78.homeMoney.config.HibernateConfiguration;
import ru.vat78.homeMoney.dao.dictionaries.DictionaryDao;
import ru.vat78.homeMoney.dao.dictionaries.TreeDictionaryDao;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;
import ru.vat78.homeMoney.model.dictionaries.TreeDictionary;

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
        Dictionary result = addingAndSearchingTest(dao, element);

        result = changeAndSaveTest(dao, result);

        dao.delete(result);
        assertEquals(dao.getCount(),cnt);
    }

    protected void makeTreeDictionaryTest(TreeDictionaryDao dao, List<? extends TreeDictionary> elements) {

        long cnt = dao.getCount();

        TreeDictionary parent = (TreeDictionary) addingAndSearchingTest(dao, elements.get(0));
        TreeDictionary child = elements.get(1);

        child.setParent(parent);
        child = (TreeDictionary) addingAndSearchingTest(dao, child);

        StringBuilder sb = new StringBuilder(parent.getFullName())
                .append("/")
                .append(child.getName());
        assertEquals(child.getFullName(),sb.toString());

        assertEquals(parent, child.getParent());

        List<TreeDictionary> topLevelParents = dao.getAllChildren(null);
        assertTrue(topLevelParents.contains(parent));

        List<TreeDictionary> children = dao.getAllChildren(parent);
        assertTrue(children.contains(child));


        //Todo: fix cascade
        dao.deleteAllChildren(parent);
        dao.delete(parent);
        assertEquals(dao.getCount(),cnt);
    }

    protected static Dictionary addingAndSearchingTest(DictionaryDao dao, Dictionary element) {

        long cnt = dao.getCount();
        dao.save(element);
        assertEquals(dao.getCount(),cnt+1);

        Dictionary result = dao.findByName(element.getName());
        assertEquals(result, element);

        result = (Dictionary) dao.findById(result.getId());
        assertEquals(result, element);

        List<Dictionary> list = dao.findAllByName("test");
        assertTrue(list.contains(result));

        return result;
    }

    protected Dictionary changeAndSaveTest(DictionaryDao dao, Dictionary element) {

        String newName = "new name";
        element.setName(newName);
        dao.save(element);

        Dictionary result = dao.findByName(newName);
        assertEquals(result, element);

        return result;
    }
}