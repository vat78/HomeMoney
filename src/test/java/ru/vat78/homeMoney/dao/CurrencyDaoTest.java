package ru.vat78.homeMoney.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import ru.vat78.homeMoney.model.Currency;

import static org.testng.Assert.*;

public class CurrencyDaoTest extends CommonEntryDaoTest {

    @Autowired
    CurrencyDao currencyDao;

    @Test
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