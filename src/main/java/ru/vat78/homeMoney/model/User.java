package ru.vat78.homeMoney.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Locale;

@Entity
@Table(name = Defenitions.TABLES.USERS, uniqueConstraints = {@UniqueConstraint(columnNames = Defenitions.FIELDS.NAME)})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min=Defenitions.USER_NAME_MIN, max=Defenitions.USER_NAME_MAX,
            message = "User name must be at least 3 characters long")
    @Pattern(regexp=Defenitions.USER_NAME_TEMPLATE,
            message="Username must be alphanumeric with no spaces")
    @Column(name = Defenitions.FIELDS.NAME, nullable = false, unique = true)
    private String name;

    @Size(min=Defenitions.PASSWORD_MIN,
            message="The password must be at least 3 characters long.")
    @Column(name = Defenitions.FIELDS.PASSWORD, nullable = false)
    private String password;

    @Column(name = Defenitions.FIELDS.FULL_NAME)
    private String fullName;

    @Column(name = Defenitions.FIELDS.IS_ADMIN)
    private boolean admin = false;

    @Column(name = Defenitions.FIELDS.LOCALE)
    private Locale locale;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
