package ru.vat78.homeMoney.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

@Entity
@Table(name = "currency", uniqueConstraints = {@UniqueConstraint(columnNames = "search_name")})
public class Currency extends Dictionary {

    @Size(min=1, max=5,
            message = "Short name must be less than 5 symbols")
    @Column
    String symbol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
