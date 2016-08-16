package ru.vat78.homeMoney.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.vat78.homeMoney.dao.accounts.CashAccountsDao;
import ru.vat78.homeMoney.dao.dictionaries.DictionaryDaoFactory;
import ru.vat78.homeMoney.dao.transactions.BillsDao;
import ru.vat78.homeMoney.model.transactions.Bill;
import ru.vat78.homeMoney.model.dictionaries.Category;
import ru.vat78.homeMoney.model.Payment;
import ru.vat78.homeMoney.model.accounts.SimpleAccount;

import java.util.Date;

import static org.testng.Assert.*;

public class BillsDaoTest extends CommonEntryDaoTest{

    @Autowired
    BillsDao billsDao;

    @Autowired
    PaymentsDao paymentsDao;

    @Autowired
    CashAccountsDao accountsDao;

    SimpleAccount account;

    @Autowired
    DictionaryDaoFactory daoFactory;
    //CategoriesDao categoriesDao;

    Category category;

    public static Bill generateTestBill(SimpleAccount testAccount) {

        Bill test = new Bill();
        test.setAccount(testAccount);
        test.setDate(new Date(System.currentTimeMillis()));
        test.setSum(1000);
        return test;
    }

    @BeforeClass()
    public void prepareData(){

        account = (SimpleAccount) addingAndSearchingTest(accountsDao, SimpleAccountsDaoTest.generateTestSimpleAccount());

        category = (Category) addingAndSearchingTest(daoFactory.getDao("categories"), CategoriesDaoTest.generateTestCategories().get(0));

    }


    @Test(groups = {"dao"})
    public void billsAndPaymentsTest(){

        long cnt = billsDao.getCount();
        long pays = paymentsDao.getCount();

        Bill test = generateTestBill(account);
        test.getPositions().add(generateTestPayment(category));
        test.getPositions().add(generateTestPayment(category));

        test = billsDao.save(test);

        getSession().getTransaction().commit();
        getSession().beginTransaction();

        assertEquals(billsDao.getCount(), cnt+1);
        assertEquals(paymentsDao.getCount(), pays+2);

        long id = test.getId();
        //test = null;
        Bill readedBill = (Bill) billsDao.findById(id);
        assertEquals(test.getPositions().size(), readedBill.getPositions().size());

        readedBill.getPositions().remove(1);
        billsDao.save(readedBill);
        assertEquals(paymentsDao.getCount(), pays+1);

        getSession().getTransaction().commit();
        getSession().beginTransaction();

        billsDao.delete(readedBill);
        assertEquals(billsDao.getCount(), cnt);
        assertEquals(paymentsDao.getCount(), pays);

    }

    private Payment generateTestPayment(Category testCategory){

        Payment test = new Payment();
        test.setCategory(testCategory);
        test.setSum(new Float(1000 * Math.random()));

        return test;
    }
}