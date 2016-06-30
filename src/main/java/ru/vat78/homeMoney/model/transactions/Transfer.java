package ru.vat78.homeMoney.model.transactions;

import com.sun.istack.internal.NotNull;
import ru.vat78.homeMoney.model.accounts.SimpleAccount;
import ru.vat78.homeMoney.model.Defenitions;

import javax.persistence.*;

@Entity
@Table(name = Defenitions.TABLES.TRANSFERS)
@DiscriminatorValue(value = Defenitions.TRANSACTION_TYPE.TRANSFER)
public class Transfer extends Transaction {

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name= Defenitions.FIELDS.CORRESPONDING_ACCOUNT, referencedColumnName = Defenitions.FIELDS.ID)
    private SimpleAccount corrAccount;

    @Column(name = Defenitions.FIELDS.CONVERSION)
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