package ru.vat78.homeMoney.model;

import com.sun.istack.internal.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

//ToDo: I could't make storing all type of accounts in one table.
@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING, length=10)
public class SimpleAccount extends Dictionary{

    @Column
    boolean active = true;

    @DateTimeFormat(pattern="dd/MM/yyyy")
    @Column(name = "opening_date")
    Date openingDate;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="currency", referencedColumnName = "id")
    private Currency currency;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}
