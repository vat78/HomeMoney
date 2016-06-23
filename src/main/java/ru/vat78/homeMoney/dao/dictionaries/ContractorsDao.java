package ru.vat78.homeMoney.dao.dictionaries;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.dictionaries.Contractor;

@Repository("contractorsDao")
@Transactional
public class ContractorsDao extends DictionaryDao<Contractor> {

    @Override
    protected Class<Contractor> getEntityClass() {return Contractor.class;}
}
