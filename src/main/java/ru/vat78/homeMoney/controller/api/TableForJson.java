package ru.vat78.homeMoney.controller.api;

import java.io.Serializable;
import java.util.List;

class TableForJson implements Serializable {

    private long total;

    private List<? extends Object> rows;

    long getTotal() {
        return total;
    }

    List<? extends Object> getRows() {
        return rows;
    }

    void setTotal(long total) {
        this.total = total;
    }

    void setRows(List<? extends Object> rows) {
        this.rows = rows;
    }
}
