package ru.vat78.homeMoney.dao.dictionaries;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.dao.dictionaries.DictionaryDao;
import ru.vat78.homeMoney.model.dictionaries.Currency;

@Repository("currenciesDao")
@Transactional
class CurrencyDao extends DictionaryDao<Currency> {

    @Override
    protected Class<Currency> getEntityClass() {return Currency.class;}

    @Override
    public Currency getNewEntity() { return new Currency();}
}
