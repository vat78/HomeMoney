package ru.vat78.homeMoney.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.vat78.homeMoney.model.User;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;

import java.lang.reflect.Type;
import java.util.Arrays;

class GsonSerializerBuilder {

    static JsonSerializer getSerializer(Class clazz){

        if (clazz.equals(User.class)) return new UserSerializer();

        if (Arrays.asList(clazz.getClasses()).contains(Dictionary.class)) return new DictionarySerializer();

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
}
