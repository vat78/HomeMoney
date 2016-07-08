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

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SimpleDictionaryService {

    /*
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
    */

    @Autowired
    DictionaryDaoFactory daoFactory;

    public long getCount(String dictionary){
        if (!checkDictionaryName(dictionary)) return 0;
        return daoFactory.getDao(dictionary).getCount();
    }

    public List<Dictionary> getRecords(String dictionary, int offset, int size, String sortColumn, String sortOrder, String searchString){
        if (!checkDictionaryName(dictionary)) return Collections.emptyList();
        return daoFactory.getDao(dictionary).getPart(offset,size,sortColumn,sortOrder,searchString);
    }

    public Dictionary getRecordByName(String dictionary, String name){
        if (!checkDictionaryName(dictionary)) return null;
        return daoFactory.getDao(dictionary).findByName(name);
    }

    public boolean checkDictionaryName(String dictionary) {
        return (daoFactory.getDao(dictionary) != null);
    }

    public boolean saveRecord(String dictionary, Dictionary entity){

        if (!checkDictionaryName(dictionary)) return false;

        try {
            daoFactory.getDao(dictionary).save(entity);
        } catch (Exception ignored) {return false;}

        return true;
    }

    public boolean deleteRecordById(String dictionary, Long id){

        if (!checkDictionaryName(dictionary)) return false;
        try {
            daoFactory.getDao(dictionary).deleteById(id);
        } catch (Exception ignored) {return false;}

        return true;
    }

    public Dictionary getNewEntry(String dictionary){
        if (!checkDictionaryName(dictionary)) return null;
        return (Dictionary) daoFactory.getDao(dictionary).getNewEntity();
    }

}
