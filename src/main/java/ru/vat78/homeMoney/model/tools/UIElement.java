package ru.vat78.homeMoney.model.tools;

import com.sun.istack.internal.NotNull;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.User;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = Defenitions.TABLES.ELEMENTS, uniqueConstraints =
@UniqueConstraint(columnNames = {
        Defenitions.FIELDS.USER,
        Defenitions.FIELDS.TYPE,
        Defenitions.FIELDS.NAME
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

    @ManyToOne()
    @JoinColumn(name=Defenitions.FIELDS.PARENT_ID)
    private UIElement parent;

    @OneToMany(mappedBy = Defenitions.FIELDS.PARENT_ID, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UIElement> children = new HashSet<UIElement>();

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
}
