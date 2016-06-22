package ru.vat78.homeMoney.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bills")
@DiscriminatorValue(value = "bill")
public class Bill extends Transaction {

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="contractor", referencedColumnName = "id")
    private Contractor contractor;

    @OneToMany(mappedBy = "bill", fetch = FetchType.EAGER, orphanRemoval=true, cascade = {CascadeType.ALL})
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
