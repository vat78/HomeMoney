package ru.vat78.homeMoney.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.Tag;

@Repository("tagsDao")
@Transactional
public class TagsDao extends DictionaryDao<Tag> {

    protected Class<Tag> getEntityClass() {return Tag.class;}
}
