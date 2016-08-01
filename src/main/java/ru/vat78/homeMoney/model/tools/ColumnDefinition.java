package ru.vat78.homeMoney.model.tools;

import ru.vat78.homeMoney.model.Defenitions;

import javax.persistence.*;

@Entity
@Table(name = Defenitions.TABLES.COLUMNS)
public class ColumnDefinition implements Comparable<ColumnDefinition> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = Defenitions.FIELDS.NAME)
    private String name;

    @Column(name = Defenitions.FIELDS.CAPTION)
    private String caption;

    @Column(name = Defenitions.FIELDS.VISIBLE)
    private boolean visible;

    @Column(name = Defenitions.FIELDS.NUM)
    private int num;

    @Column()
    private String type;

    private boolean editable;

    private boolean shown;

    @Override
    public int compareTo(ColumnDefinition o) {
        return num - o.getNum();
    }

    public ColumnDefinition setName(String name) {
        this.name = name;
        return this;
    }

    public ColumnDefinition setEditable(boolean editable) {
        this.editable = editable;
        return this;
    }

    public ColumnDefinition setShown(boolean shown) {
        this.shown = shown;
        return this;
    }

    public ColumnDefinition setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isEditable() {
        return editable;
    }

    public boolean isShown() {
        return shown;
    }

    public String getCaption() {
        return caption;
    }

    public boolean isVisible() {
        return visible;
    }

    public long getId() {
        return id;
    }

    public int getNum() {
        return num;
    }

    public String getType() {
        return type;
    }
}
