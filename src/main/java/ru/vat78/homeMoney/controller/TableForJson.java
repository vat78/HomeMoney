package ru.vat78.homeMoney.controller;

import java.io.Serializable;
import java.util.List;

public class TableForJson implements Serializable {

    private long total;

    private List<? extends Object> rows;

    public long getTotal() {
        return total;
    }

    public List<? extends Object> getRows() {
        return rows;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setRows(List<? extends Object> rows) {
        this.rows = rows;
    }
}
