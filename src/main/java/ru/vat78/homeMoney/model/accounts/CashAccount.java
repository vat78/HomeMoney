package ru.vat78.homeMoney.model.accounts;

import ru.vat78.homeMoney.model.Defenitions;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = Defenitions.FIELDS.ID)
public class CashAccount extends SimpleAccount{
}
