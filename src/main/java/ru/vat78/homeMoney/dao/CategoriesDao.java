package ru.vat78.homeMoney.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.Category;

@Repository("categoriesDao")
@Transactional
public class CategoriesDao extends TreeDictionaryDao<Category> {

    protected Class<Category> getEntityClass() {return Category.class;}
}
