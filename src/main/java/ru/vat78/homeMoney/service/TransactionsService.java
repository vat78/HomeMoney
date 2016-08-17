package ru.vat78.homeMoney.service;

import org.springframework.stereotype.Service;
import ru.vat78.homeMoney.model.accounts.SimpleAccount;
import ru.vat78.homeMoney.model.transactions.Transaction;

import java.util.Collections;
import java.util.List;

@Service
public class TransactionsService {

    public List<Transaction> getTransactionsByAccount(SimpleAccount account, int offset, int size, String sortColumn, String sortOrder, String searchString){
        return Collections.emptyList();
    }
}
