package ru.vat78.homeMoney.model.transactions;

import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.dictionaries.Contractor;
import ru.vat78.homeMoney.model.Payment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Defenitions.TABLES.BILLS)
@DiscriminatorValue(value = Defenitions.TRANSACTION_TYPE.BILL)
public class Bill extends Transaction {

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name= Defenitions.FIELDS.CONTRACTOR_ID, referencedColumnName = Defenitions.FIELDS.ID)
    private Contractor contractor;

    @OneToMany(mappedBy = Defenitions.FIELDS.BILL_ID, fetch = FetchType.EAGER, orphanRemoval=true, cascade = {CascadeType.ALL})
    List<Payment> positions = new ArrayList<Payment>();

    public Contractor getContractor() {
        return contractor;
    }

    public List<Payment> getPositions() {
        return positions;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public void setPositions(List<Payment> positions) {
        this.positions = positions;
    }
}
