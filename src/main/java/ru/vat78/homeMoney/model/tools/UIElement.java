package ru.vat78.homeMoney.model.tools;

import com.sun.istack.internal.NotNull;
import org.hibernate.annotations.SortComparator;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.User;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = Defenitions.TABLES.ELEMENTS, uniqueConstraints =
@UniqueConstraint(columnNames = {
        Defenitions.FIELDS.USER,
        Defenitions.FIELDS.TYPE,
        Defenitions.FIELDS.NAME,
        Defenitions.FIELDS.PARENT_ID
}))
public class UIElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name= Defenitions.FIELDS.USER, referencedColumnName = Defenitions.FIELDS.ID)
    private User user;

    @Column(name = Defenitions.FIELDS.NAME)
    private String name;

    @Column(name = Defenitions.FIELDS.TYPE)
    private String type;

    @Column(name = Defenitions.FIELDS.POSITION)
    private int position;

    @ManyToOne()
    @JoinColumn(name=Defenitions.FIELDS.PARENT_ID)
    private UIElement parent;

    @OneToMany(mappedBy = Defenitions.FIELDS.PARENT_ID, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy(Defenitions.FIELDS.POSITION + "," + Defenitions.FIELDS.NAME + " ASC")
    private Set<UIElement> children = new TreeSet<UIElement>(new UIElementComporator());

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, String> parameters;

    public UIElement() {
        parameters = new HashMap<String, String>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UIElement getParent() {
        return parent;
    }

    public void setParent(UIElement parent) {
        this.parent = parent;
    }

    public Set<UIElement> getChildren() {
        return children;
    }

    public void setChildren(Set<UIElement> children) {
        this.children = children;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void resetChildren() {
        children = new TreeSet<UIElement>(new UIElementComporator());
    }

    private class UIElementComporator implements Comparator<UIElement> {

        @Override
        public int compare(UIElement o1, UIElement o2) {

            if (o1.getPosition() > o2.getPosition()) return 1;
            if (o1.getPosition() < o2.getPosition()) return -1;
            return o1.getName().compareTo(o2.getName());
        }
    }
}
