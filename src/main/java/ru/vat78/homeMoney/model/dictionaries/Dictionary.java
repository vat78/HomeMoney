package ru.vat78.homeMoney.model.dictionaries;

import com.google.gson.annotations.Expose;
import com.sun.istack.internal.NotNull;
import ru.vat78.homeMoney.model.CommonEntry;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.UIDef;

import javax.persistence.*;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class Dictionary extends CommonEntry implements Comparable<Dictionary> {

    @UIDef(caption = "Name", shown = true, editable = true, num = 10)
    @Column (name = Defenitions.FIELDS.NAME)
    @NotNull
    @Size(min=Defenitions.DICTIONARY_NAME_MIN, max=Defenitions.DICTIONARY_NAME_MAX,
            message="Name must be at least 2 characters long.")
    protected String name;

    @Expose(serialize = false, deserialize = false)
    @Column(name = Defenitions.FIELDS.SEARCH_NAME)
    protected String searchingName;

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

    protected String makeSearchingName(String name) {
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

    @Override
    public int compareTo(Dictionary o) {
        if (equals(o)) return 0;
        return name.compareTo(o.getName());
    }
}
