package ru.vat78.homeMoney.model.dictionaries;

import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.UIDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

@Entity
@Table(name = Defenitions.TABLES.CURRENCY, uniqueConstraints = {@UniqueConstraint(columnNames = Defenitions.FIELDS.SEARCH_NAME)})
public class Currency extends Dictionary {

    @UIDef(caption = "Symbol", shown = true, editable = true, order = 20)
    @Size(min=Defenitions.CURRNECY_SYMBOL_MIN, max=Defenitions.CURRNECY_SYMBOL_MAX,
            message = "Short name must be less than 5 symbols")
    @Column(name = Defenitions.FIELDS.SYMBOL)
    String symbol;

    public Currency() {
        this.setComponent(Defenitions.GROUPS.DICTIONARIES);
        this.setType(Defenitions.TABLES.CURRENCY);
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
