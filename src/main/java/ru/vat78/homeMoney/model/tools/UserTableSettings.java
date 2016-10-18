package ru.vat78.homeMoney.model.tools;

import com.sun.istack.internal.NotNull;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.User;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = Defenitions.TABLES.TABLE_SETTINGS, uniqueConstraints =
        @UniqueConstraint(columnNames = {
                Defenitions.FIELDS.USER,
                Defenitions.FIELDS.NAME
}))
public class UserTableSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name= Defenitions.FIELDS.USER, referencedColumnName = Defenitions.FIELDS.ID)
    private User user;

    @Column(name = Defenitions.FIELDS.NAME)
    private String name;

    @Column(name = Defenitions.FIELDS.CAPTION)
    private String caption;

    @Column(name = Defenitions.FIELDS.SORT_COLUMN)
    private String sortColumn;

    @Column(name = Defenitions.FIELDS.SORT_ORDER)
    private String sortOrder;

    @Column(name = Defenitions.FIELDS.PAGE_SIZE)
    private int pageSize;

    @Column(name = Defenitions.FIELDS.ADD_BTN)
    private boolean addBtn;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @MapKey(name = Defenitions.FIELDS.NAME)
    private Map<String, ColumnDefinition> columns;

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setColumns(Map<String, ColumnDefinition> columns) {
        this.columns = columns;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getCaption() {
        return caption;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Map<String, ColumnDefinition> getColumns() {
        return columns;
    }

    public boolean isAddBtn() {
        return addBtn;
    }

    public void setAddBtn(boolean addBtn) {
        this.addBtn = addBtn;
    }
}
