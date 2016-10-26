package ru.vat78.homeMoney.controller.api;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Response implements Serializable {

    static final String ERROR = "FAIL";
    static final String SUCCESS = "SUCCESS";

    private String status = null;

    private Object result = null;

    String getStatus() {
        return status;
    }

    Object getResult() {
        return result;
    }

    void setStatus(String status) {
        this.status = status;
    }

    void setResult(Object result) {
        this.result = result;
    }

    void setError(String field, String message){
        setStatus(ERROR);
        List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
        errorMesages.add(new ErrorMessage(field, new LocaleMessage(message)));
        setResult(errorMesages);
    }

    boolean isResultSet() { return  result != null;}
}
