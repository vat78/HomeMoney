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

    public Category getParent() {
        return parent;
    }

    public void setParent(TreeDictionary parent) {
        if (parent instanceof Category) this.parent = (Category) parent;
    }

    @OneToMany(mappedBy = Defenitions.FIELDS.PARENT_ID, fetch = FetchType.LAZY, orphanRemoval=true, cascade = CascadeType.ALL)
    private Set<Category> children = new HashSet<Category>();

    public Set<Category> getChildren(){
        return children;
    }

}
