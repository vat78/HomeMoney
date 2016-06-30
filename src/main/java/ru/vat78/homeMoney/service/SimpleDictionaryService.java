package ru.vat78.homeMoney.service;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.DataBinder;
import ru.vat78.homeMoney.dao.UsersDao;
import ru.vat78.homeMoney.dao.dictionaries.*;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class SimpleDictionaryService {

    @Autowired
    UsersDao usersDao;

    @Autowired
    ContractorsDao contractorsDao;

    @Autowired
    CurrencyDao currencyDao;

    @Autowired
    PersonsDao personsDao;

    @Autowired
    TagsDao tagsDao;

    private Map<String, DictionaryDao> dbEngines;

    public long getCount(String dictionary){
        if (!checkDictionaryName(dictionary)) return 0;
        return dbEngines.get(dictionary).getCount();
    }

    public List<Dictionary> getRecords(String dictionary, int offset, int size, String sortColumn, String sortOrder){
        if (!checkDictionaryName(dictionary)) return Collections.emptyList();
        return dbEngines.get(dictionary).getPart(offset,size,sortColumn,sortOrder);
    }

    public Dictionary getRecordByName(String dictionary, String name){
        if (!checkDictionaryName(dictionary)) return null;
        return dbEngines.get(dictionary).findByName(name);
    }

    public boolean checkDictionaryName(String dictionary) {
        if (dbEngines == null) fillMap();
        return dbEngines.containsKey(dictionary);
    }

    public boolean saveRecord(String dictionary, Dictionary entity){

        if (!checkDictionaryName(dictionary)) return false;

        try {
            dbEngines.get(dictionary).save(entity);
        } catch (Exception ignored) {return false;}

        return true;
    }

    public Dictionary convertToDBObject(String dictionary, Map<String,String> data){
        if (!checkDictionaryName(dictionary)) return null;
        PropertyValues values = convertMapToProperties(data);
        Dictionary entity = (Dictionary) dbEngines.get(dictionary).getNewEntity();
        DataBinder binder = new DataBinder(entity);
        binder.bind(values);
        if (binder.getBindingResult().hasErrors()) return null;
        return entity;
    }

    private void fillMap(){
        dbEngines = new HashMap<String, DictionaryDao>();
        dbEngines.put(Defenitions.TABLES.CONTRACTORS, contractorsDao);
        dbEngines.put(Defenitions.TABLES.CURRENCY, currencyDao);
        dbEngines.put(Defenitions.TABLES.PERSONS, personsDao);
        dbEngines.put(Defenitions.TABLES.TAGS, tagsDao);
    }

    private PropertyValues convertMapToProperties(Map<String,String> data){

        MutablePropertyValues values = new MutablePropertyValues();
        for (String field : data.keySet()){
            if (field.equals(Defenitions.FIELDS.ID)){
                if (data.get(field) == null || data.get(field).length() == 0) {
                    values.add(field, 0);
                } else {
                    values.add(field, Long.valueOf(data.get(field)));
                }
                continue;
            }
            if (field.equals(Defenitions.FIELDS.NAME)){
                values.add(field,data.get(field));
                continue;
            }
        }
        return values;
    }
}
