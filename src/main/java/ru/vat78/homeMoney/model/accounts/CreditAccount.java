package ru.vat78.homeMoney.model.accounts;

import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.UIDef;
import ru.vat78.homeMoney.model.dictionaries.Contractor;

import javax.persistence.*;

@Entity
@Table(name = Defenitions.TABLES.CREDIT_ACCOUNTS)
@PrimaryKeyJoinColumn(name = Defenitions.FIELDS.ID)
public class CreditAccount extends Account {

    @UIDef(caption = "Bank", shown = true, editable = true, num = 33)
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name= Defenitions.FIELDS.ORGANIZATION, referencedColumnName = Defenitions.FIELDS.ID)
    private Contractor creditOrganization;

    @UIDef(caption = "Credit rate", shown = true, editable = true, num = 37, type = "number")
    @Column(name = Defenitions.FIELDS.CREDIT_RATE)
    private float rate;

    public CreditAccount() {
        this.setGroup(Defenitions.GROUPS.ACCOUNTS);
        this.setType(Defenitions.TABLES.CREDIT_ACCOUNTS);
    }

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
