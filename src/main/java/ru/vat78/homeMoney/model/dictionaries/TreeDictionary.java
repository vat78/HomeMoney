package ru.vat78.homeMoney.model.dictionaries;

import javax.persistence.*;
import java.util.Set;

@MappedSuperclass
public abstract class TreeDictionary extends Dictionary {

    private String fullName = null;

    private int level = 0;

    public abstract void setParent(TreeDictionary parent);

    public abstract TreeDictionary getParent();

    public abstract Set<? extends TreeDictionary> getChildren();

    public String getFullName(boolean rebuild){
        if (fullName == null || rebuild) fullName = buildFullName();
        return fullName;
    }

    @Override
    public void setName(String name) {
        this.name = name;
        getFullName(true);
        this.searchingName = makeSearchingName(name);
    }

    public int getLevel(){
        return level;
    }

    public void setLevel(){
        level = getParent().getLevel() + 1;
    }

    private String buildFullName() {

        StringBuilder sb = new StringBuilder(getName());
        TreeDictionary parent = getParent();
        if (parent != null) sb.insert(0,"/").insert(0,parent.getFullName(false));
        return sb.toString();

    }

}
