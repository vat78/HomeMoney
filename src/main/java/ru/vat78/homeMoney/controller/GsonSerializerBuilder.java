package ru.vat78.homeMoney.controller;

import com.google.gson.*;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.User;
import ru.vat78.homeMoney.model.accounts.SimpleAccount;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;
import ru.vat78.homeMoney.model.dictionaries.TreeDictionary;

import java.lang.reflect.Type;
import java.util.Arrays;

class GsonSerializerBuilder {

    static JsonSerializer getSerializer(Class clazz){

        if (clazz.equals(User.class)) return new UserSerializer();

        if (clazz.getSuperclass().equals(Dictionary.class) || clazz.equals(Dictionary.class)) return new DictionarySerializer();

        if (clazz.equals(TreeDictionary.class) || clazz.getSuperclass().equals(TreeDictionary.class)) return new TreeSerializer();

        return null;
    }

    static class UserSerializer implements JsonSerializer<User>{

        public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context){
            if (src.getFullName() == null) return new JsonPrimitive(src.getName());
            return new JsonPrimitive(src.getFullName());
        }

    }

    static class DictionarySerializer implements JsonSerializer<Dictionary>{

        public JsonElement serialize(Dictionary src, Type typeOfSrc, JsonSerializationContext context){
            return new JsonPrimitive(src.getName());
        }

    }

    static class TreeSerializer implements JsonSerializer<TreeDictionary>{

        public JsonElement serialize(TreeDictionary src, Type typeOfSrc, JsonSerializationContext context){
            JsonObject result = new JsonObject();
            result.addProperty(Defenitions.FIELDS.ID, src.getId());
            result.addProperty(Defenitions.FIELDS.NAME, src.getName());
            result.addProperty("level", src.getLevel());
            result.addProperty("node", 0);

            return result;
        }
    }

    static class SimpleAccountSerializer implements JsonSerializer<SimpleAccount>{

        public JsonElement serialize(SimpleAccount src, Type typeOfSrc, JsonSerializationContext context){

            JsonObject result = new JsonObject();
            result.addProperty(Defenitions.FIELDS.ID, src.getId());
            result.addProperty(Defenitions.FIELDS.NAME, src.getName());
            result.addProperty(Defenitions.FIELDS.OPENING_DATE, src.getOpeningDate().getTime());
            result.addProperty(Defenitions.FIELDS.CURRENCY, src.getCurrency().getName());

            return result;
        }
    }
}
