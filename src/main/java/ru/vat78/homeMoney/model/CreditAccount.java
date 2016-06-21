package ru.vat78.homeMoney.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "credit")
public class CreditAccount extends SimpleAccount {

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="organization", referencedColumnName = "id")
    private Contractor creditOrganization;

    @Column(name = "credit_rate")
    private float rate;

}
