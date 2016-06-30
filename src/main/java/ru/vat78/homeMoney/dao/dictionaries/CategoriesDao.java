package ru.vat78.homeMoney.dao.dictionaries;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.dictionaries.Category;

@Repository("categoriesDao")
@Transactional
public class CategoriesDao extends TreeDictionaryDao<Category> {

    @Override
    protected Class<Category> getEntityClass() {return Category.class;}

    @Override
    public Category getNewEntity() { return new Category();}
}
