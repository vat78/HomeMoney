package ru.vat78.homeMoney.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import ru.vat78.homeMoney.model.Category;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class CategoriesDaoTest extends CommonEntryDaoTest {

    @Autowired
    CategoriesDao categoriesDao;

    @Test
    public void CategoryTreeDictionaryTest(){

        makeTreeDictionaryTest(categoriesDao, generateTestCategoryes());

    }

    private List<Category> generateTestCategoryes() {

        List<Category> result = new ArrayList<Category>();
        result.add(new Category());
        result.add(new Category());

        String name = " Test category " + Math.random();
        result.get(0).setName("Parent" + name);
        result.get(1).setName("Child" + name);

        return result;
    }

}