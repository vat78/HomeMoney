package ru.vat78.homeMoney.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.Currency;

@Repository("currenciesDao")
@Transactional
public class CurrencyDao extends DictionaryDao<Currency> {

    protected Class<Currency> getEntityClass() {return Currency.class;}
}
