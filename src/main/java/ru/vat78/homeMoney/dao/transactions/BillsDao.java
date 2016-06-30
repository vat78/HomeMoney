package ru.vat78.homeMoney.dao.transactions;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.transactions.Bill;
import ru.vat78.homeMoney.model.Payment;

@Repository("billsDao")
@Transactional
public class BillsDao extends TransactionDao {

    @Override
    protected Class<Bill> getEntityClass() { return Bill.class;}

    @Override
    public Bill getNewEntity() { return new Bill();}

    @Transactional(readOnly = false)
    public Bill save(Bill entity){

        if (entity.getId() == null)
            entity = checkLinksInPositions(entity);
        return (Bill) super.save(entity);
    }

    private Bill checkLinksInPositions(Bill bill){
        for(Payment position: bill.getPositions()){
            position.setBill(bill);
        }
        return bill;
    }
}
