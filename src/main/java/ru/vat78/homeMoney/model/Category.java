package ru.vat78.homeMoney.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories", uniqueConstraints = {@UniqueConstraint(columnNames = "search_name")})
public class Category extends TreeDictionary {

    @ManyToOne()
    @JoinColumn(name="parent_id")
    private Category parent;

    public Category getParent() {
        return parent;
    }

    public void setParent(TreeDictionary parent) {
        if (parent instanceof Category) this.parent = (Category) parent;
    }

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, orphanRemoval=true, cascade = CascadeType.ALL)
    private Set<Category> children = new HashSet<Category>();

    public Set<Category> getChildren(){
        return children;
    }

}
