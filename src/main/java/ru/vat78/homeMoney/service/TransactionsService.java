package ru.vat78.homeMoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vat78.homeMoney.dao.transactions.TransactionDao;
import ru.vat78.homeMoney.model.accounts.Account;
import ru.vat78.homeMoney.model.transactions.Transaction;

import java.util.List;

@Service
public class TransactionsService extends CommonService<Transaction> {

    @Autowired
    TransactionDao transactionsDao;

    public List<Transaction> getTransactionsByAccount(Account account, int offset, int size, String sortColumn, String sortOrder, String searchString){
        return transactionsDao.getPartForAccount(account, offset,size,sortColumn,sortOrder,searchString);
    }

    @Override
    public List<Transaction> getRecords(String type, int offset, int size, String sortColumn, String sortOrder, String searchString) {
        return null;
    }

    @Override
    public Transaction getRecordByName(String type, String name) {
        return null;
    }

    @Override
    public Transaction getRecordById(String dictionary, Long id) {
        return null;
    }

    @Override
    public boolean isTypeExist(String type) {
        return false;
    }

    @Override
    public boolean deleteRecordById(String Type, Long id) {
        return false;
    }

    @Override
    public boolean deleteRecord(Transaction entry) {
        return false;
    }

    public Transaction getRecordById(long id){
        return null;
    }

    public Transaction getNewEntry(String transactionType) {

        Transaction result = null;
        try {
            result  = transactionsDao.getNewEntity(transactionType);
        } catch (Exception ignored) {}
        return result;
    }

    public boolean saveRecord(Transaction entity) {
        if (!isTypeExist(entity.getType())) return false;
        try {
            entity = (Transaction) transactionsDao.save(entity);
        } catch (Exception ignored) {return false;}

        return entity != null;
    }

}
