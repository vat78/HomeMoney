package ru.vat78.homeMoney.model.accounts;

import com.sun.istack.internal.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.dictionaries.Currency;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;

import javax.persistence.*;
import java.util.Date;

//ToDo: I could't make storing all type of accounts in one table.
@Entity
@Table(name = Defenitions.TABLES.ACCOUNTS)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = Defenitions.FIELDS.ACCOUNT_TYPE, discriminatorType = DiscriminatorType.STRING, length=Defenitions.DISCRIMINATOR_LENGTH)
public class SimpleAccount extends Dictionary {

    @Column(name = Defenitions.FIELDS.ACTIVE)
    boolean active = true;

    @DateTimeFormat(pattern=Defenitions.DATE_FORMAT)
    @Column(name = Defenitions.FIELDS.OPENING_DATE)
    Date openingDate;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name=Defenitions.FIELDS.CURRENCY, referencedColumnName = Defenitions.FIELDS.ID)
    private Currency currency;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}
