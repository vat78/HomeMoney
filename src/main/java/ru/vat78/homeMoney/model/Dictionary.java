package ru.vat78.homeMoney.model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class Dictionary extends CommonEntry {

    @Column
    @NotNull
    @Size(min=2, max=250,
            message="Name must be at least 2 characters long.")
    private String name;

    @Column(name = "search_name")
    private String searchingName;

    public String getName() {
        return name;
    }

    public String getSearchingName() {
        return searchingName;
    }

    public void setName(String name) {
        this.name = name;
        this.searchingName = makeSearchingName(name);
    }

    private String makeSearchingName(String name) {
        return name.toLowerCase();
    }

    @Override
    public boolean equals(Object object){

        if (object == null || object.getClass() != this.getClass()) return false;
        return ((Dictionary) object).getSearchingName().equals(searchingName);

    }

    @Override
    public int hashCode(){

        if (searchingName == null) return 0;
        return searchingName.hashCode();

    }
}
