package ru.vat78.homeMoney.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.Transfer;

@Repository("transfersDao")
@Transactional
public class TransferDao extends TransactionDao {

    @Override
    protected Class<Transfer> getEntityClass() { return Transfer.class;}

}
