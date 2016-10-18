package ru.vat78.homeMoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vat78.homeMoney.dao.transactions.TransactionsDaoFactory;
import ru.vat78.homeMoney.model.accounts.Account;
import ru.vat78.homeMoney.model.transactions.Transaction;

import java.util.List;

@Service
public class TransactionsService {

    @Autowired
    TransactionsDaoFactory daoFactory;

    public List<Transaction> getTransactionsByAccount(Account account, int offset, int size, String sortColumn, String sortOrder, String searchString){
        return daoFactory.getDao(null).getPartForAccount(account, offset,size,sortColumn,sortOrder,searchString);
    }

    public Transaction getTransactionById(long id){
        return null;
    }

    public Transaction getNewEntry(String transactionType) {
        return (Transaction) daoFactory.getDao(transactionType).getNewEntity();
    }

    public boolean saveRecord(Transaction entity) {
        if (!checkTransactionType(entity.getType())) return false;
        try {
            entity = (Transaction) daoFactory.getDao(entity.getType()).save(entity);
        } catch (Exception ignored) {return false;}

        return entity != null;
    }

    public boolean checkTransactionType(String transactionType) {
        return (daoFactory.getDao(transactionType) != null);
    }
}
