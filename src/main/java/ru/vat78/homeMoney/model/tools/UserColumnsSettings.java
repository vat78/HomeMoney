package ru.vat78.homeMoney.model.tools;

import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.User;

//ToDo: save setting in DB
public class UserColumnsSettings {

    private User user;

    private int num;

    private String tableName;

    private String columnName;

    private String visible;

    public UserColumnsSettings setNum(int num) {
        this.num = num;
        return this;
    }

    public UserColumnsSettings setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public UserColumnsSettings setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public UserColumnsSettings setVisible(boolean visible) {
        if (visible) {
            this.visible = Defenitions.TRUE;
        } else {
            this.visible = Defenitions.FALSE;
        }
        return this;
    }

    public UserColumnsSettings setUser(User user) {
        this.user = user;
        return this;
    }

    public int getNum() {
        return num;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getVisible() {
        return visible;
    }

    public User getUser() {
        return user;
    }
}
