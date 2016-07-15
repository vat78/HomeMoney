package ru.vat78.homeMoney.dao.dictionaries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vat78.homeMoney.model.Defenitions;

import java.util.HashMap;
import java.util.Map;

@Component
public class DictionaryDaoFactory {

    private Map<String, DictionaryDao> dbEngines;

    @Autowired
    ContractorsDao contractorsDao;

    @Autowired
    CurrencyDao currencyDao;

    @Autowired
    PersonsDao personsDao;

    @Autowired
    TagsDao tagsDao;

    @Autowired
    CategoriesDao categoriesDao;


    public DictionaryDao getDao(String dictionary){
        if (dbEngines == null) fillMap();
        return dbEngines.get(dictionary);
    }

    public TreeDictionaryDao getTreeDao(String dictionary){
        if (dbEngines == null) fillMap();
        if (dictionary.equals(Defenitions.TABLES.CATEGORIES)) return (TreeDictionaryDao) dbEngines.get(dictionary);
        return null;
    }

    private void fillMap(){
        dbEngines = new HashMap<String, DictionaryDao>();
        dbEngines.put(Defenitions.TABLES.CONTRACTORS, contractorsDao);
        dbEngines.put(Defenitions.TABLES.CURRENCY, currencyDao);
        dbEngines.put(Defenitions.TABLES.PERSONS, personsDao);
        dbEngines.put(Defenitions.TABLES.TAGS, tagsDao);
        dbEngines.put(Defenitions.TABLES.CATEGORIES, categoriesDao);
    }

}
