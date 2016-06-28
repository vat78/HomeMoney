package ru.vat78.homeMoney.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;
import ru.vat78.homeMoney.dao.dictionaries.TagsDao;
import ru.vat78.homeMoney.model.dictionaries.Tag;

import java.util.*;

@Controller
@RequestMapping("/ajax")
public class AjaxController {

    @Autowired
    TagsDao tagsDao;

    @RequestMapping(value = "/tags", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getTagsTable(@RequestParam Map<String,String> allRequestParams){

        List<Tag> result = tagsDao.getPart(Integer.valueOf(allRequestParams.get("offset")),
                Integer.valueOf(allRequestParams.get("limit")),
                allRequestParams.get("sort"),allRequestParams.get("order"));
        if (result == null) result = Collections.emptyList();
        Gson gson = new Gson();
        String gres = gson.toJson(result);
        return gson.toJson(result);
    }


}
