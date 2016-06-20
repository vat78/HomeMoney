package ru.vat78.homeMoney.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.Contractor;

@Repository("contractorsDao")
@Transactional
public class ContractorsDao extends DictionaryDao<Contractor> {

    protected Class<Contractor> getEntityClass() {return Contractor.class;}
}
