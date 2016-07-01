package ru.vat78.homeMoney.controller;

import java.util.List;

class Response {

    private String status;

    private Object result;

    public String getStatus() {
        return status;
    }

    public Object getResult() {
        return result;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
