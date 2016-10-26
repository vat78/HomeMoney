package ru.vat78.homeMoney.controller.api;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import ru.vat78.homeMoney.service.SecurityService;

import java.lang.reflect.Type;

@Component
public class MyGsonBuilder {

    @Autowired
    SecurityService securityService;

    @Autowired
    MessageSource messageSource;

    GsonBuilder getGsonBuilder(){

        GsonBuilder result = new GsonBuilder()
                .registerTypeAdapter(LocaleMessage.class, new LocaleMessageSerializer());
        return result;
    }

    class LocaleMessageSerializer implements JsonSerializer<LocaleMessage> {

        public JsonElement serialize(LocaleMessage src, Type typeOfSrc, JsonSerializationContext context) {

            String result;
            try {
                 result = messageSource.getMessage(src.getMessage(), null, securityService.getCurrentUser().getLocale());
            } catch (NoSuchMessageException err) {
                result = "No lang: " + src.getMessage();
            }
            return new JsonPrimitive(result);
        }
    }
}
