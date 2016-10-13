package ru.vat78.homeMoney.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.vat78.homeMoney.dao.accounts.CashAccountsDao;
import ru.vat78.homeMoney.dao.transactions.BillsDao;
import ru.vat78.homeMoney.dao.transactions.TransactionDao;
import ru.vat78.homeMoney.dao.transactions.TransferDao;
import ru.vat78.homeMoney.model.transactions.Bill;
import ru.vat78.homeMoney.model.accounts.Account;
import ru.vat78.homeMoney.model.transactions.Transaction;
import ru.vat78.homeMoney.model.transactions.Transfer;

import java.util.Date;
import java.util.List;

import static org.testng.Assert.*;

public class TransactionDaoTest extends CommonEntryDaoTest {

    @Autowired
    TransactionDao transactionsDao;

    @Autowired
    BillsDao billsDao;

    @Autowired
    TransferDao transferDao;

    @Autowired
    CashAccountsDao accountsDao;

    Account account;


    @BeforeClass()
    public void prepareData(){

        account = (Account) addingAndSearchingTest(accountsDao, SimpleAccountsDaoTest.generateTestSimpleAccount());

    }

    @Test(groups = {"dao"})
    public void transactionsTests(){

        long cnt = transactionsDao.getCount();

        Bill bill = (Bill) addTransaction(billsDao, BillsDaoTest.generateTestBill(account));
        Transfer tr = (Transfer) addTransaction(transferDao, generateTestTransfer());

        assertEquals(transactionsDao.getCount(), cnt + 2);

        List<Transaction> transactions = transactionsDao.findAllForAccount(account);
        assertEquals(transactions.size(), 2);

        transactionsDao.delete(bill);
        transactionsDao.delete(tr);

        assertEquals(transactionsDao.getCount(), cnt);
    }

    private Transfer generateTestTransfer() {

        Transfer test = new Transfer();
        test.setAccount(account);
        test.setDate(new Date(System.currentTimeMillis()));
        test.setSum(1000);
        test.setCorrAccount(account);
        return test;
    }

    private Transaction addTransaction(TransactionDao dao, Transaction entity) {

        long cnt = dao.getCount();
        return (Transaction) dao.save(entity);

    }
}