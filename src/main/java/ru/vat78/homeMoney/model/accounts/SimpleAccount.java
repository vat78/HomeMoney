package ru.vat78.homeMoney.model.accounts;

import com.sun.istack.internal.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.UIDef;
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

    @UIDef(caption = "Active", shown = true, editable = true, num = 40, type = "checkbox")
    @Column(name = Defenitions.FIELDS.ACTIVE)
    boolean active = true;

    @UIDef(caption = "Open date", shown = true, editable = true, num = 20, type = "date")
    @DateTimeFormat(pattern=Defenitions.DATE_FORMAT)
    @Column(name = Defenitions.FIELDS.OPENING_DATE)
    Date openingDate;

    @UIDef(caption = "Currency", shown = true, editable = true, num = 30)
    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name=Defenitions.FIELDS.CURRENCY, referencedColumnName = Defenitions.FIELDS.ID)
    private Currency currency;

    public boolean isActive() {
        return active;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
