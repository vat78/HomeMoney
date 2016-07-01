package ru.vat78.homeMoney.dao.dictionaries;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.dictionaries.Person;

@Repository("personsDao")
@Transactional
class PersonsDao extends DictionaryDao<Person> {

    @Override
    protected Class<Person> getEntityClass() {return Person.class;}

    @Override
    public Person getNewEntity() { return new Person();}
}
