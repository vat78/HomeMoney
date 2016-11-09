package ru.vat78.homeMoney.controller.api;

import com.sun.istack.internal.NotNull;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.bind.WebDataBinder;
import ru.vat78.homeMoney.model.CommonEntry;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;
import ru.vat78.homeMoney.service.CommonService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ApiTools {

    static void checkUniqueName(@NotNull CommonService service, @NotNull Dictionary entity, @NotNull Response response){

        String objectType = entity.getType();
        if (!response.isResultSet()) {
            CommonEntry inDB = service.getRecordByName(objectType, entity.getName());
            if (inDB != null && !inDB.getId().equals(entity.getId())){
                response.setError(Defenitions.FIELDS.NAME, "edit.error.nameExists");
            }
        }
    }

    static void saveEntityToDb(@NotNull CommonService service, @NotNull CommonEntry entity, @NotNull Response response) {

        if (!response.isResultSet()) {
            if (service.saveRecord(entity)) {
                response.setStatus(Response.SUCCESS);
                response.setResult(entity);
            } else {
                response.setError(Defenitions.FIELDS.NAME, "edit.error.unknown");
            }
        }
    }

    static void deleteEntry(@NotNull CommonService service, String objectType, String id, @NotNull Response response) {

        CommonEntry entry = loadEntryFromDb(service, objectType, id);

        if (entry == null) {

            response.setError("", "edit.error.noObject");

        } else {

            if (service.deleteRecord(entry)) {
                response.setStatus(Response.SUCCESS);
            } else {
                response.setError("", "edit.error.objectIsUsed");
            }
        }
    }

    static void validateEntry(@NotNull CommonEntry entity, @NotNull Response response) {

        if (!response.isResultSet()) {

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set result = validator.validate(entity);

            if (result.size() > 0) {
                response.setStatus(Response.ERROR);
                List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
                for (Object val : result) {
                    ConstraintViolation error = (ConstraintViolation) val;
                    errorMesages.add(new ErrorMessage(error.getPropertyPath().toString(), new LocaleMessage(error.getMessage())));
                }
                response.setResult(errorMesages);
            }
        }
    }

    static CommonEntry buildEntryFromParams(@NotNull CommonService service, @NotNull Map<String,String> params, @NotNull Response response) {

        CommonEntry result;

        if (params.get(Defenitions.FIELDS.ID) != null && parseId(params.get(Defenitions.FIELDS.ID)) != 0 ) {
            result = loadEntryFromDb(service, params.get(Defenitions.FIELDS.TYPE), params.get(Defenitions.FIELDS.ID));
        } else {
            result = service.getNewEntry(params.get(Defenitions.FIELDS.TYPE));
        }

        if (result == null) {

            response.setError("", "edit.error.noType");

        } else {

            WebDataBinder binder = new WebDataBinder(result);
            binder.bind(new MutablePropertyValues(params));
        }

        return result;
    }

    static CommonEntry loadEntryFromDb(@NotNull CommonService service, String type, String id) {

        if (type == null || parseId(id) == 0) return null;

        return service.getRecordById(type, parseId(id));
    }

    static Long parseId(String id) {

        Long result = 0L;

        try {
            result = Long.parseLong(id);
        } catch (Exception ignored) {}

        return result;
    }
}
