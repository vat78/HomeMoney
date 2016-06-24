package ru.vat78.homeMoney.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import ru.vat78.homeMoney.dao.dictionaries.CurrencyDao;
import ru.vat78.homeMoney.model.dictionaries.Currency;

public class CurrencyDaoTest extends CommonEntryDaoTest {

    @Autowired
    CurrencyDao currencyDao;

    @Test(groups = {"dao"})
    public void currencyDictionaryTest() {

        makeDictionaryTest(currencyDao, getTestCurrency());

    }

    private Currency getTestCurrency(){

        Currency test = new Currency();
        test.setName("Test contractor " + Math.random());
        test.setSymbol("test");
        return test;
    }

}