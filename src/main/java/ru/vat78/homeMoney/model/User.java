package ru.vat78.homeMoney.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min=3, max=50,
            message = "User name must be at least 3 characters long")
    @Pattern(regexp="^[a-zA-Z0-9]+$",
            message="Username must be alphanumeric with no spaces")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Size(min=3, max=250,
            message="The password must be at least 3 characters long.")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name="is_admin")
    private boolean admin = false;

}
