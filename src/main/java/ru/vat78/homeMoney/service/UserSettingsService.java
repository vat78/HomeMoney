package ru.vat78.homeMoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import ru.vat78.homeMoney.dao.UserTablesSettingsDao;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.UIDef;
import ru.vat78.homeMoney.model.User;
import ru.vat78.homeMoney.model.accounts.CashAccount;
import ru.vat78.homeMoney.model.accounts.CreditAccount;
import ru.vat78.homeMoney.model.accounts.Account;
import ru.vat78.homeMoney.model.dictionaries.*;
import ru.vat78.homeMoney.model.dictionaries.Currency;
import ru.vat78.homeMoney.model.tools.UIElement;
import ru.vat78.homeMoney.model.transactions.Transaction;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class UserSettingsService {

    @Autowired
    UserTablesSettingsDao tablesSettingsDao;

    @Autowired
    MessageSource messageSource;

    public UIElement getTableSettings(User user, String tableName){
        UIElement res= tablesSettingsDao.getTableSettings(user, tableName);
        if (res == null) {
            res = getDefaultSettings(tableName, user);
            res.setUser(user);
            tablesSettingsDao.save(res);
        }


        Locale l = user.getLocale();

        String s;
        try {
            s = messageSource.getMessage(res.getType() + "." + tableName, null, l);
        } catch (NoSuchMessageException e) {
            s = "No lang: " + res.getType() + "." + tableName;
        }
        res.getParameters().put("caption",s);

        for (UIElement child : res.getChildren()) {
            try {
                s = messageSource.getMessage(child.getType() + "." + child.getName(), null, l);
            } catch (NoSuchMessageException e) {
                s = "No lang: " + child.getType() + "." + child.getName();
            }
            child.getParameters().put("caption", s);
        }
        return res;
    }

    private UIElement getDefaultSettings(String tableName, User user) {

        //String t = tableName;
        //if (t.contains(Defenitions.TABLES.TRANSACTIONS)) t= Defenitions.TABLES.TRANSACTIONS;
        UIElement result = new UIElement();
        result.setName(tableName);
        result.setType("table");
        result.setUser(user);
        result.getParameters().put("pageSize","10");
        result.getParameters().put("sortColumn",Defenitions.FIELDS.ID);
        result.getParameters().put("sortOrder","asc");

        result.resetChildren();
        getTableColumns(result);

        return result;
    }

    private void getTableColumns(UIElement table){

        Class tableClass = getTableClass(table.getName());
        if (tableClass != null) getFieldsUpTo(table, tableClass);
    }

    private Class getTableClass(String tableName){

        if (tableName.equals(Defenitions.TABLES.CATEGORIES)) return Category.class;
        if (tableName.equals(Defenitions.TABLES.CONTRACTORS)) return Contractor.class;
        if (tableName.equals(Defenitions.TABLES.CURRENCY)) return Currency.class;
        if (tableName.equals(Defenitions.TABLES.PERSONS)) return Person.class;
        if (tableName.equals(Defenitions.TABLES.TAGS)) return Tag.class;

        if (tableName.equals(Defenitions.TABLES.ACCOUNTS) || tableName.equals("closed")) return Account.class;
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

        return null;
    }

    private void getFieldsUpTo(UIElement table, Class<?> startClass) {

        for (Field field : startClass.getDeclaredFields()){

            UIDef[] a = field.getAnnotationsByType(UIDef.class);
            if (a.length > 0){

                UIElement newCol = new UIElement();
                newCol.setParent(table);
                newCol.setName(field.getName());
                newCol.setUser(table.getUser());
                newCol.setType("field");
                newCol.setPosition(a[0].order());

                newCol.getParameters().put("editable", String.valueOf(a[0].editable()));
                newCol.getParameters().put("shown", String.valueOf(a[0].shown()));
                newCol.getParameters().put("control",a[0].control());
                newCol.getParameters().put("dataSource",a[0].source());
                newCol.getParameters().put("visible", String.valueOf(field.getName().equals(Defenitions.FIELDS.NAME)));
                table.getChildren().add(newCol);
            }
        }
        Class<?> parentClass = startClass.getSuperclass();

        if (parentClass != null) {
            getFieldsUpTo(table, parentClass);
        }
    }
}
