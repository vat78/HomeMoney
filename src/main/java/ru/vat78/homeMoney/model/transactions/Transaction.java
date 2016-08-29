package ru.vat78.homeMoney.model.transactions;

import com.sun.istack.internal.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import ru.vat78.homeMoney.model.UIDef;
import ru.vat78.homeMoney.model.accounts.SimpleAccount;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = Defenitions.FIELDS.OPERATION, discriminatorType = DiscriminatorType.STRING, length= Defenitions.DISCRIMINATOR_LENGTH)
public class Transaction extends CommonEntry {

    @UIDef(caption = "Date", shown = true, editable = true, num = 20)
    @DateTimeFormat(pattern=Defenitions.DATE_FORMAT)
    @Column(name = Defenitions.FIELDS.DATE, nullable = false)
    private Date date;

    @NotNull
    @UIDef(caption = "Account", shown = false, num = 10)
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name= Defenitions.FIELDS.ACCOUNT_ID, referencedColumnName = Defenitions.FIELDS.ID)
    private SimpleAccount account;

    @UIDef(caption = "Summ", shown = true, editable = true, num = 50)
    @Column(name = Defenitions.FIELDS.SUM)
    float sum;

    @UIDef(caption = "Operation", shown = false, editable = true, num = 40)
    @Column(name = Defenitions.FIELDS.OPERATION_TYPE)
    int operation;

    @UIDef(caption = "Contractor", shown = false, editable = true, num = 30)
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name= Defenitions.FIELDS.CONTRACTOR_ID, referencedColumnName = Defenitions.FIELDS.ID)
    private Contractor contractor;

    public Date getDate() {
        return date;
    }

    public SimpleAccount getAccount() {
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

    public void setAccount(SimpleAccount account) {
        this.account = account;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public Transaction setDefaultValues(SimpleAccount account){
        setDate(new Date(System.currentTimeMillis()));
        setAccount(account);
        return this;
    }
}
