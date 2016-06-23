package ru.vat78.homeMoney.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import ru.vat78.homeMoney.dao.dictionaries.TagsDao;
import ru.vat78.homeMoney.model.dictionaries.Tag;


public class TagsDaoTest extends CommonEntryDaoTest {

    @Autowired
    TagsDao tagsDao;

    @Test
    public void tagsDictonaryTest(){

        makeDictionaryTest(tagsDao, getTestTag());

    }

    private Tag getTestTag() {

        Tag template = new Tag();
        template.setName("Test tag " + Math.random());
        return template;

    }
}