package ru.vat78.homeMoney.model.transactions;

import com.sun.istack.internal.NotNull;
import ru.vat78.homeMoney.model.accounts.Account;
import ru.vat78.homeMoney.model.Defenitions;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = Defenitions.TABLES.TRANSFERS)
@PrimaryKeyJoinColumn(name = Defenitions.FIELDS.ID)
public class Transfer extends Transaction {

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name= Defenitions.FIELDS.CORRESPONDING_ACCOUNT, referencedColumnName = Defenitions.FIELDS.ID)
    private Account corrAccount;

    @Column(name = Defenitions.FIELDS.CONVERSION)
    private float conversion;

    public Account getCorrAccount() {
        return corrAccount;
    }

    public float getConversion() {
        return conversion;
    }

    public void setCorrAccount(Account corrAccount) {
        this.corrAccount = corrAccount;
    }

    public void setConversion(float conversion) {
        this.conversion = conversion;
    }

    @Override
    public Transaction setDefaultValues(Account account){
        setDate(new Date(System.currentTimeMillis()));
        setAccount(account);
        setConversion(1);
        return this;
    }
}
