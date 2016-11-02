package ru.vat78.homeMoney.model.transactions;

import com.sun.istack.internal.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import ru.vat78.homeMoney.model.UIDef;
import ru.vat78.homeMoney.model.accounts.Account;
import ru.vat78.homeMoney.model.CommonEntry;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.dictionaries.Contractor;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = Defenitions.TABLES.TRANSACTIONS)
public class Transaction extends CommonEntry {

    @UIDef(caption = "Date", shown = true, editable = true, order = 20)
    @DateTimeFormat(pattern=Defenitions.DATE_FORMAT)
    @Column(name = Defenitions.FIELDS.DATE, nullable = false)
    private Date date;

    @NotNull
    @UIDef(caption = "Account", shown = false, order = 10)
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name= Defenitions.FIELDS.ACCOUNT_ID, referencedColumnName = Defenitions.FIELDS.ID)
    private Account account;

    @UIDef(caption = "Summ", shown = true, editable = true, order = 50)
    @Column(name = Defenitions.FIELDS.SUM)
    float sum;

    @UIDef(caption = "Operation", shown = false, editable = true, order = 40)
    @Column(name = Defenitions.FIELDS.OPERATION_TYPE)
    int operation;

    @UIDef(caption = "Contractor", shown = false, editable = true, order = 30)
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name= Defenitions.FIELDS.CONTRACTOR_ID, referencedColumnName = Defenitions.FIELDS.ID)
    private Contractor contractor;

    public Date getDate() {
        return date;
    }

    public Account getAccount() {
        return account;
    }

    public float getSum() {
        return sum;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean setDate(String date, String dateFormat) {
        DateFormat format = new SimpleDateFormat(dateFormat);
        try {
            this.date = format.parse(date);
        } catch (ParseException ex) {return false;}
        return true;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public Transaction setDefaultValues(Account account){
        setDate(new Date(System.currentTimeMillis()));
        setAccount(account);
        return this;
    }
}
