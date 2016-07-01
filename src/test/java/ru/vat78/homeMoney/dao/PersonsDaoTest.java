package ru.vat78.homeMoney.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import ru.vat78.homeMoney.dao.dictionaries.DictionaryDaoFactory;
import ru.vat78.homeMoney.model.dictionaries.Person;

public class PersonsDaoTest extends CommonEntryDaoTest{

    @Autowired
    DictionaryDaoFactory daoFactory;
    //PersonsDao personsDao;

    @Test(groups = {"dao"})
    public void personDictionaryTest() {

        makeDictionaryTest(daoFactory.getDao("persons"), getTestPerson());

    }

    private Person getTestPerson(){

        Person test = new Person();
        test.setName("Test person " + Math.random());
        return test;
    }
}