package ru.vat78.homeMoney.dao.transactions;

import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vat78.homeMoney.model.Defenitions;

import java.util.HashMap;
import java.util.Map;

@Component
public class TransactionsDaoFactory {

    private Map<String, TransactionDao> dbEngines;

    @Autowired
    BillsDao billsDao;

    @Autowired
    TransferDao transferDao;

    public TransactionDao getDao(@Nullable String type){
        if (dbEngines == null) fillMap();
        if (type == null) type = Defenitions.TABLES.BILLS;
        return dbEngines.get(type);
    }

    private void fillMap() {
        dbEngines = new HashMap<String, TransactionDao>();
        dbEngines.put(Defenitions.TABLES.BILLS, billsDao);
        dbEngines.put(Defenitions.TABLES.TRANSFERS, transferDao);
    }
}
