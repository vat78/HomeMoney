package ru.vat78.homeMoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vat78.homeMoney.dao.UserTablesSettingsDao;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.UIDef;
import ru.vat78.homeMoney.model.User;
import ru.vat78.homeMoney.model.accounts.CashAccount;
import ru.vat78.homeMoney.model.accounts.CreditAccount;
import ru.vat78.homeMoney.model.accounts.SimpleAccount;
import ru.vat78.homeMoney.model.dictionaries.*;
import ru.vat78.homeMoney.model.dictionaries.Currency;
import ru.vat78.homeMoney.model.tools.ColumnDefinition;
import ru.vat78.homeMoney.model.tools.UserTableSettings;
import ru.vat78.homeMoney.model.transactions.Transaction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class UserSettingsService {

    @Autowired
    UserTablesSettingsDao tablesSettingsDao;

    public UserTableSettings getTableSettings(User user, String tableName){
        UserTableSettings res= tablesSettingsDao.getTableSettings(user, tableName);
        if (res == null) {
            res = getDefaultSettings(tableName);
            res.setUser(user);
            tablesSettingsDao.save(res);
        }
        return res;
    }

    private UserTableSettings getDefaultSettings(String tableName) {

        String t = tableName;
        if (t.contains(Defenitions.TABLES.TRANSACTIONS)) t= Defenitions.TABLES.TRANSACTIONS;
        UserTableSettings result = new UserTableSettings();
        result.setName(tableName);
        result.setCaption(t);
        result.setPageSize(10);
        result.setSortColumn(Defenitions.FIELDS.ID);
        result.setSortOrder("asc");

        result.setColumns(getTableColumns(t));

        return result;
    }

    private Map<String, ColumnDefinition> getTableColumns(String tableName){

        Map<String, ColumnDefinition> result = new HashMap<String, ColumnDefinition>();
        Class table = getTableClass(tableName);
        if (table == null) return result;

        return getFieldsUpTo(table);
    }

    private Class getTableClass(String tableName){

        Class result = null;

        if (tableName.equals(Defenitions.TABLES.CATEGORIES)) return Category.class;
        if (tableName.equals(Defenitions.TABLES.CONTRACTORS)) return Contractor.class;
        if (tableName.equals(Defenitions.TABLES.CURRENCY)) return Currency.class;
        if (tableName.equals(Defenitions.TABLES.PERSONS)) return Person.class;
        if (tableName.equals(Defenitions.TABLES.TAGS)) return Tag.class;

        if (tableName.equals(Defenitions.TABLES.ACCOUNTS) || tableName.equals("closed")) return SimpleAccount.class;
        if (tableName.equals(Defenitions.TABLES.CASH_ACCOUNTS)) return CashAccount.class;
        if (tableName.equals(Defenitions.TABLES.CREDIT_ACCOUNTS)) return CreditAccount.class;

        if (tableName.equals(Defenitions.TABLES.TRANSACTIONS)) return Transaction.class;

        /*
        String path = "ru.vat78.homeMoney.model";
        String[] packages = {"",".accounts",".dictionaries",".transactions"};

        for (int i=0; i<packages.length && result == null; i++){
            StringBuilder fullname = new StringBuilder(path)
                    .append(packages[i])
                    .append(".")
                    .append(tableName);
            try {
                result = Class.forName(fullname.toString());
            } catch (Exception ignored) {}

        }*/

        return result;
    }

    private Map<String, ColumnDefinition> getFieldsUpTo(Class<?> startClass) {

        Map<String, ColumnDefinition> currentClassFields = new HashMap<String, ColumnDefinition>();
        for (Field field : startClass.getDeclaredFields()){

            UIDef[] a = field.getAnnotationsByType(UIDef.class);
            if (a.length > 0){

                ColumnDefinition newCol = new ColumnDefinition();
                newCol.setName(field.getName());
                newCol.setCaption(field.getName());
                newCol.setEditable(a[0].editable());
                newCol.setShown(a[0].shown());
                newCol.setNum(a[0].num());
                newCol.setType(a[0].type());
                newCol.setVisible(field.getName().equals(Defenitions.FIELDS.NAME));
                currentClassFields.put(field.getName(),newCol);
            }
        }
        Class<?> parentClass = startClass.getSuperclass();

        if (parentClass != null) {
            Map<String, ColumnDefinition> parentClassFields = getFieldsUpTo(parentClass);
            currentClassFields.putAll(parentClassFields);
        }

        return currentClassFields;
    }
}
