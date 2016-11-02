package ru.vat78.homeMoney.model.accounts;

import org.springframework.format.annotation.DateTimeFormat;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.UIDef;
import ru.vat78.homeMoney.model.dictionaries.Currency;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = Defenitions.TABLES.ACCOUNTS)
public class Account extends Dictionary {

    @UIDef(caption = "Active", shown = true, editable = true, order = 40, control = UIDef.CHECKBOX)
    @Column(name = Defenitions.FIELDS.ACTIVE)
    boolean active = true;

    @UIDef(caption = "Open date", shown = true, editable = true, order = 20, control = UIDef.DATE)
    @DateTimeFormat(pattern=Defenitions.DATE_FORMAT)
    @Column(name = Defenitions.FIELDS.OPENING_DATE)
    Date openingDate;

    @UIDef(caption = "Currency", shown = true, editable = true, order = 30, control = UIDef.SELECT, source = Defenitions.TABLES.CURRENCY)
    @NotNull(message = "Please, select currency")
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name=Defenitions.FIELDS.CURRENCY, referencedColumnName = Defenitions.FIELDS.ID)
    private Currency currency;

    //@UIDef(caption = "Type", shown = false, editable = true, order = 50, control = "text")
    //@Column(name = Defenitions.FIELDS.ACCOUNT_TYPE)
    //private String accountType;

    public boolean isActive() {
        return active;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public Currency getCurrency() {
        return currency;
    }

    //public String getAccountType() {
    //    return accountType;
    //}

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public void setOpeningDate(String openingDate, String dateFormat) {
        DateFormat format = new SimpleDateFormat(dateFormat);
        try {
            this.openingDate = format.parse(openingDate);
        } catch (ParseException ex) {}
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    //public void setAccountType(String accountType) {
    //    this.accountType = accountType;
    //}
}
