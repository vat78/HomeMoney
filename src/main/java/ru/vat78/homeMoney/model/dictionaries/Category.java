package ru.vat78.homeMoney.model.dictionaries;

import ru.vat78.homeMoney.model.Defenitions;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Defenitions.TABLES.CATEGORIES, uniqueConstraints = {@UniqueConstraint(columnNames = Defenitions.FIELDS.SEARCH_NAME)})
public class Category extends TreeDictionary {

    @ManyToOne()
    @JoinColumn(name=Defenitions.FIELDS.PARENT_ID)
    private Category parent;

    @OneToMany(mappedBy = Defenitions.FIELDS.PARENT_ID, fetch = FetchType.EAGER, orphanRemoval=true, cascade = CascadeType.ALL)
    private Set<Category> children = new HashSet<Category>();

    public Category() {
        this.setComponent(Defenitions.GROUPS.DICTIONARIES);
        this.setType(Defenitions.TABLES.CATEGORIES);
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(TreeDictionary parent) {
        if (parent instanceof Category) {
            this.parent = (Category) parent;
            this.searchingName = makeSearchingName(getFullName());
            setLevel();
        }
    }

    public Set<Category> getChildren(){
        return children;
    }



}
