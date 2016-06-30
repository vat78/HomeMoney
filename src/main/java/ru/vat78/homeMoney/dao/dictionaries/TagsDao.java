package ru.vat78.homeMoney.dao.dictionaries;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vat78.homeMoney.model.dictionaries.Tag;

@Repository("tagsDao")
@Transactional
public class TagsDao extends DictionaryDao<Tag> {

    @Override
    protected Class<Tag> getEntityClass() {return Tag.class;}

    @Override
    public Tag getNewEntity(){ return new Tag();}
}
