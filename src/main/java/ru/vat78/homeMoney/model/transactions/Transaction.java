package ru.vat78.homeMoney.model.transactions;

import com.sun.istack.internal.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import ru.vat78.homeMoney.model.accounts.SimpleAccount;
import ru.vat78.homeMoney.model.CommonEntry;
import ru.vat78.homeMoney.model.Defenitions;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = Defenitions.TABLES.TRANSACTIONS)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = Defenitions.FIELDS.OPERATION, discriminatorType = DiscriminatorType.STRING, length= Defenitions.DISCRIMINATOR_LENGTH)
public abstract class Transaction extends CommonEntry {

    @DateTimeFormat(pattern=Defenitions.DATE_FORMAT)
    @Column(name = Defenitions.FIELDS.DATE, nullable = false)
    private Date date;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name= Defenitions.FIELDS.ACCOUNT_ID, referencedColumnName = Defenitions.FIELDS.ID)
    private SimpleAccount account;

    @Column(name = Defenitions.FIELDS.SUM)
    float sum;

    @Column(name = Defenitions.FIELDS.OPERATION_TYPE)
    int operation;

    public Date getDate() {
        return date;
    }

    public SimpleAccount getAccount() {
        return account;
    }

    public float getSum() {
        return sum;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAccount(SimpleAccount account) {
        this.account = account;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

}
