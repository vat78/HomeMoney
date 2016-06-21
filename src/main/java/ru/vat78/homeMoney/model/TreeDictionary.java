package ru.vat78.homeMoney.model;

import javax.persistence.*;
import java.util.Set;

@MappedSuperclass
public abstract class TreeDictionary extends Dictionary {

    private String fullName = null;

    public abstract void setParent(TreeDictionary parent);

    public abstract TreeDictionary getParent();

    public abstract Set<? extends TreeDictionary> getChildren();

    public String getFullName(){
        if (fullName == null) fullName = buildFullName();
        return fullName;
    }

    private String buildFullName() {

        StringBuilder sb = new StringBuilder(getName());
        TreeDictionary parent = getParent();
        if (parent != null) sb.insert(0,"/").insert(0,parent.getFullName());
        return sb.toString();

    }

}
