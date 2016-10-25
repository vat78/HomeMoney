package ru.vat78.homeMoney.service;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.DataBinder;
import ru.vat78.homeMoney.dao.UsersDao;
import ru.vat78.homeMoney.dao.dictionaries.*;
import ru.vat78.homeMoney.model.CommonEntry;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;
import ru.vat78.homeMoney.model.dictionaries.TreeDictionary;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

@Service
public class DictionaryService {

    @Autowired
    DictionaryDao dictionaryDao;

    public long getCount(String dictionary){
        if (!checkDictionaryName(dictionary)) return 0;
        return dictionaryDao.getCount(dictionary);
    }

    public List<Dictionary> getRecords(String dictionary, int offset, int size, String sortColumn, String sortOrder, String searchString){
        if (!checkDictionaryName(dictionary)) return Collections.emptyList();
        return dictionaryDao.getPart(dictionary,offset,size,sortColumn,sortOrder,searchString);
    }

    public Dictionary getRecordByName(String dictionary, String name){
        if (!checkDictionaryName(dictionary)) return null;
        return dictionaryDao.findByName(dictionary, name);
    }

    public boolean checkDictionaryName(String dictionary) {
        return (getDictionaryClass(dictionary) != CommonEntry.class);
    }

    public boolean saveRecord(Dictionary entity){

        if (!checkDictionaryName(entity.getType())) return false;

        try {
            entity = (Dictionary) dictionaryDao.save(entity);
        } catch (Exception ignored) {return false;}

        return entity != null;
    }

    public boolean deleteRecordById(String dictionary, Long id){

        if (!checkDictionaryName(dictionary)) return false;
        try {
            dictionaryDao.deleteById(dictionary, id);
        } catch (Exception ignored) {return false;}

        return true;
    }

    public boolean deleteRecord(Dictionary entry){
        dictionaryDao.delete(entry);
        return true;
    }

    public Dictionary getNewEntry(String dictionary){

        Dictionary result = null;
        try {
            result = (Dictionary) dictionaryDao.getNewEntity(dictionary);
        } catch (Exception ignored) {}
        return result;
    }

    public Set<Dictionary> getTreeRecords(String dictionary, Long id) {
        if (!checkDictionaryName(dictionary)) return Collections.emptySet();
        Set<Dictionary>  result = dictionaryDao.getAllChildrenById(dictionary, id);
        if (id == 0 && result.size() < 1) return insertFirstElement(dictionary);
        return result;
    }

    public Class getEntityClass(String dictionary){
        if (!checkDictionaryName(dictionary)) return null;
        return getNewEntry(dictionary).getClass();
    }

    public Dictionary getRecordById(String dictionary, long id) {
        if (!checkDictionaryName(dictionary)) return null;
        return (Dictionary) dictionaryDao.findById(dictionary, id);
    }

    public Dictionary getRecordById(String dictionary, String id) {

        Long resId;
        try {
            resId = Long.valueOf(id);
        } catch (NumberFormatException ex){
            return null;
        }

        return getRecordById(dictionary, resId);
    }

    private Set<Dictionary> insertFirstElement(String dictionary) {
        if (!checkDictionaryName(dictionary)) return Collections.emptySet();
        Dictionary element = (Dictionary) getNewEntry(dictionary);
        element.setName("Sample value");
        dictionaryDao.save(element);
        return dictionaryDao.getAllChildrenById(dictionary, 0);
    }

    private Class getDictionaryClass(String dictionary) {
        return dictionaryDao.getEntityClass(dictionary);
    }
}
