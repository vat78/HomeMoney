package ru.vat78.homeMoney.model.accounts;

import ru.vat78.homeMoney.model.Defenitions;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = Defenitions.TABLES.CASH_ACCOUNTS)
@PrimaryKeyJoinColumn(name = Defenitions.FIELDS.ID)
public class CashAccount extends Account {

    public CashAccount() {
        this.setGroup(Defenitions.TABLES.ACCOUNTS);
        this.setType(Defenitions.TABLES.CASH_ACCOUNTS);
    }
}
