package ru.vat78.homeMoney.service;

import ru.vat78.homeMoney.model.CommonEntry;
import ru.vat78.homeMoney.model.exceptions.WrongTypeException;

import java.util.List;

public abstract class CommonService<T extends CommonEntry> {

    public abstract List<T> getRecords(String type, int offset, int size, String sortColumn, String sortOrder, String searchString) throws WrongTypeException;

    public abstract T getRecordByName(String type, String name) throws WrongTypeException;

    public abstract T getRecordById(String type, Long id) throws WrongTypeException;

    public abstract boolean isTypeExist(String type) throws WrongTypeException;

    public abstract boolean saveRecord(T entity);

    public abstract boolean deleteRecordById(String Type, Long id) throws WrongTypeException;

    public abstract boolean deleteRecord(T entry);

    public abstract T getNewEntry(String type) throws WrongTypeException;

}
