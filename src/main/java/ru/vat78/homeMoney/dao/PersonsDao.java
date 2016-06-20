package ru.vat78.homeMoney.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.Person;

@Repository("personsDao")
@Transactional
public class PersonsDao extends DictionaryDao<Person> {

    protected Class<Person> getEntityClass() {return Person.class;}
}
