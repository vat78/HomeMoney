package ru.vat78.homeMoney.model.accounts;

import ru.vat78.homeMoney.model.Defenitions;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = Defenitions.ACCOUNTS_TYPE.CASH)
public class CashAccount extends SimpleAccount{
}
