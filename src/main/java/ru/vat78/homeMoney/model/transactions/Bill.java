package ru.vat78.homeMoney.model.transactions;

import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.Payment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Defenitions.TABLES.BILLS)
@PrimaryKeyJoinColumn(name = Defenitions.FIELDS.ID)
public class Bill extends Transaction {

    @OneToMany(mappedBy = Defenitions.FIELDS.BILL_ID, fetch = FetchType.EAGER, orphanRemoval=true, cascade = {CascadeType.ALL})
    List<Payment> positions = new ArrayList<Payment>();

    public Bill() {
        this.setComponent(Defenitions.TABLES.TRANSACTIONS);
        this.setType(Defenitions.TABLES.BILLS);
    }

    public List<Payment> getPositions() {
        return positions;
    }

    public void setPositions(List<Payment> positions) {
        this.positions = positions;
    }
}
