package ru.vat78.homeMoney.model;

import com.sun.istack.internal.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "operation", discriminatorType = DiscriminatorType.STRING, length=10)
public abstract class Transaction extends CommonEntry {

    @DateTimeFormat(pattern="dd/MM/yyyy")
    @Column(name = "date", nullable = false)
    private Date date;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="account", referencedColumnName = "id")
    private SimpleAccount account;

    @Column
    float sum;

    @Column(name = "operation_type")
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
