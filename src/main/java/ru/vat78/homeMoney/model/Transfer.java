package ru.vat78.homeMoney.model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "transfers")
@DiscriminatorValue(value = "transfer")
public class Transfer extends Transaction {

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="corresponding", referencedColumnName = "id")
    private SimpleAccount corrAccount;

    @Column
    private float conversion;

    public SimpleAccount getCorrAccount() {
        return corrAccount;
    }

    public float getConversion() {
        return conversion;
    }

    public void setCorrAccount(SimpleAccount corrAccount) {
        this.corrAccount = corrAccount;
    }

    public void setConversion(float conversion) {
        this.conversion = conversion;
    }
}
