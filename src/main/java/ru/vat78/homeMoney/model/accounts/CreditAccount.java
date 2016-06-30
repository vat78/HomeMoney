package ru.vat78.homeMoney.model.accounts;

import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.dictionaries.Contractor;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = Defenitions.ACCOUNTS_TYPE.CREDIT)
public class CreditAccount extends SimpleAccount {

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name= Defenitions.FIELDS.ORGANIZATION, referencedColumnName = Defenitions.FIELDS.ID)
    private Contractor creditOrganization;

    @Column(name = Defenitions.FIELDS.CREDIT_RATE)
    private float rate;

    public Contractor getCreditOrganization() {
        return creditOrganization;
    }

    public float getRate() {
        return rate;
    }

    public void setCreditOrganization(Contractor creditOrganization) {
        this.creditOrganization = creditOrganization;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}