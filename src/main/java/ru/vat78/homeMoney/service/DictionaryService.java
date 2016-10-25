package ru.vat78.homeMoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vat78.homeMoney.dao.dictionaries.*;
import ru.vat78.homeMoney.model.CommonEntry;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;

import java.util.*;

@Service
public class DictionaryService extends CommonService<Dictionary> {

    @Autowired
    DictionaryDao dictionaryDao;

    public long getCount(String dictionary){
        if (!isTypeExist(dictionary)) return 0;
        return dictionaryDao.getCount(dictionary);
    }

    public List<Dictionary> getRecords(String dictionary, int offset, int size, String sortColumn, String sortOrder, String searchString){
        if (!isTypeExist(dictionary)) return Collections.emptyList();
        return dictionaryDao.getPart(dictionary,offset,size,sortColumn,sortOrder,searchString);
    }

    public Dictionary getRecordByName(String dictionary, String name){
        if (!isTypeExist(dictionary)) return null;
        return dictionaryDao.findByName(dictionary, name);
    }

    public boolean isTypeExist(String dictionary) {
        return (getDictionaryClass(dictionary) != CommonEntry.class);
    }

    public boolean saveRecord(Dictionary entity){

        if (!isTypeExist(entity.getType())) return false;

        try {
            entity = (Dictionary) dictionaryDao.save(entity);
        } catch (Exception ignored) {return false;}

        return entity != null;
    }

    public boolean deleteRecordById(String dictionary, Long id){

        if (!isTypeExist(dictionary)) return false;
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
            result = dictionaryDao.getNewEntity(dictionary);
        } catch (Exception ignored) {}
        return result;
    }

    public Set<Dictionary> getTreeRecords(String dictionary, Long id) {
        if (!isTypeExist(dictionary)) return Collections.emptySet();
        Set<Dictionary>  result = dictionaryDao.getAllChildrenById(dictionary, id);
        if (id == 0 && result.size() < 1) return insertFirstElement(dictionary);
        return result;
    }

    public Dictionary getRecordById(String dictionary, Long id) {
        if (!isTypeExist(dictionary)) return null;
        return (Dictionary) dictionaryDao.findById(dictionary, id);
    }

    private Set<Dictionary> insertFirstElement(String dictionary) {
        if (!isTypeExist(dictionary)) return Collections.emptySet();
        Dictionary element = getNewEntry(dictionary);
        element.setName("Sample value");
        dictionaryDao.save(element);
        return dictionaryDao.getAllChildrenById(dictionary, 0);
    }

    public Class getDictionaryClass(String dictionary) {
        return dictionaryDao.getEntityClass(dictionary);
    }
}
