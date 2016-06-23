package ru.vat78.homeMoney.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = Defenitions.TABLES.USERS, uniqueConstraints = {@UniqueConstraint(columnNames = Defenitions.FIELDS.NAME)})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min=Defenitions.USER_NAME_MIN, max=Defenitions.USER_NAME_MAX,
            message = "User name must be at least 3 characters long")
    @Pattern(regexp=Defenitions.USER_NAME_TEMPLATE,
            message="Username must be alphanumeric with no spaces")
    @Column(name = Defenitions.FIELDS.NAME, nullable = false, unique = true)
    private String name;

    @Size(min=Defenitions.PASSWORD_MIN, max=Defenitions.PASSWORD_MAX,
            message="The password must be at least 3 characters long.")
    @Column(name = Defenitions.FIELDS.PASSWORD, nullable = false)
    private String password;

    @Column(name = Defenitions.FIELDS.FULL_NAME)
    private String fullName;

    @Column(name= Defenitions.FIELDS.IS_ADMIN)
    private boolean admin = false;

}
