package ru.vat78.homeMoney.model.dictionaries;

import ru.vat78.homeMoney.model.Defenitions;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = Defenitions.TABLES.CONTRACTORS, uniqueConstraints = {@UniqueConstraint(columnNames = Defenitions.FIELDS.SEARCH_NAME)})
public class Contractor extends Dictionary {

    public Contractor() {
        this.setComponent(Defenitions.GROUPS.DICTIONARIES);
        this.setType(Defenitions.TABLES.CONTRACTORS);
    }
}
