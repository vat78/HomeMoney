package ru.vat78.homeMoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vat78.homeMoney.dao.UsersDao;
import ru.vat78.homeMoney.dao.dictionaries.*;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public boolean checkDictionaryName(String dictionary) {
        if (dbEngines == null) fillMap();
        return dbEngines.containsKey(dictionary);
    }

    private void fillMap(){
        dbEngines = new HashMap<String, DictionaryDao>();
        dbEngines.put(Defenitions.TABLES.CONTRACTORS, contractorsDao);
        dbEngines.put(Defenitions.TABLES.CURRENCY, currencyDao);
        dbEngines.put(Defenitions.TABLES.PERSONS, personsDao);
        dbEngines.put(Defenitions.TABLES.TAGS, tagsDao);
    }
}
