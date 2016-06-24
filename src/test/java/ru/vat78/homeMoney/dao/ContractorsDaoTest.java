package ru.vat78.homeMoney.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import ru.vat78.homeMoney.dao.dictionaries.ContractorsDao;
import ru.vat78.homeMoney.model.dictionaries.Contractor;


public class ContractorsDaoTest extends CommonEntryDaoTest {

    @Autowired
    ContractorsDao contractorsDao;

    @Test(groups = {"dao"})
    public void contractorDictionaryTest() {

        makeDictionaryTest(contractorsDao, getTestContractor());

    }

    private Contractor getTestContractor(){

        Contractor test = new Contractor();
        test.setName("Test contractor " + Math.random());
        return test;
    }
}