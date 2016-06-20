package ru.vat78.homeMoney.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "persons", uniqueConstraints = {@UniqueConstraint(columnNames = "search_name")})
public class Person extends Dictionary {
}
