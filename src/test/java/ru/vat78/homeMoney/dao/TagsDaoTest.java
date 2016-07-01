package ru.vat78.homeMoney.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import ru.vat78.homeMoney.dao.dictionaries.DictionaryDaoFactory;
import ru.vat78.homeMoney.model.dictionaries.Tag;


public class TagsDaoTest extends CommonEntryDaoTest {

    @Autowired
    DictionaryDaoFactory daoFactory;
    //TagsDao tagsDao;

    @Test(groups = {"dao"})
    public void tagsDictonaryTest(){

        makeDictionaryTest(daoFactory.getDao("tags"), getTestTag());

    }

    private Tag getTestTag() {

        Tag template = new Tag();
        template.setName("Test tag " + Math.random());
        return template;

    }
}