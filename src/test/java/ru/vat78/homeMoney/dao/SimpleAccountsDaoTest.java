package ru.vat78.homeMoney.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import ru.vat78.homeMoney.dao.accounts.CreditAccountsDao;
import ru.vat78.homeMoney.dao.accounts.SimpleAccountsDao;
import ru.vat78.homeMoney.model.accounts.CreditAccount;
import ru.vat78.homeMoney.model.accounts.SimpleAccount;

import java.util.List;

import static org.testng.Assert.*;


public class SimpleAccountsDaoTest extends CommonEntryDaoTest {

    @Autowired
    SimpleAccountsDao accountsDao;

    @Autowired
    CreditAccountsDao creditDao;

    public static SimpleAccount generateTestSimpleAccount() {

        SimpleAccount test = new SimpleAccount();
        test.setName("Test simple account " + Math.random());
        test.setActive(true);
        return test;
    }

    public static CreditAccount generateTestCreditAccount() {

        CreditAccount test = new CreditAccount();
        test.setName("Test credit account " + Math.random());
        test.setActive(true);
        test.setRate(0.1f);
        return test;
    }

    @Test(groups = {"dao"})
    public void accountsTests(){

        long cnt = accountsDao.getCount();

        SimpleAccount a1 = (SimpleAccount) addingAndSearchingTest(accountsDao, generateTestSimpleAccount());
        CreditAccount a2 = (CreditAccount) addingAndSearchingTest(creditDao, generateTestCreditAccount());

        List<SimpleAccount> all = accountsDao.getAll();
        assertEquals(accountsDao.getCount(), cnt + 2);

        accountsDao.delete(a1);
        accountsDao.delete(a2);

        assertEquals(accountsDao.getCount(), cnt);

    }

}