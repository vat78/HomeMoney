package ru.vat78.homeMoney.service;

import ru.vat78.homeMoney.model.CommonEntry;

import java.util.List;

public abstract class CommonService<T extends CommonEntry> {

    public abstract List<T> getRecords(String type, int offset, int size, String sortColumn, String sortOrder, String searchString);

    public abstract T getRecordByName(String type, String name);

    public abstract T getRecordById(String dictionary, Long id);

    public abstract boolean isTypeExist(String type);

    public abstract boolean saveRecord(T entity);

    public abstract boolean deleteRecordById(String Type, Long id);

    public abstract boolean deleteRecord(T entry);

    public abstract T getNewEntry(String type);

}
