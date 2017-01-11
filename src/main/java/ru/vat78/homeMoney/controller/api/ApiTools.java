package ru.vat78.homeMoney.controller.api;

import com.sun.istack.internal.NotNull;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.bind.WebDataBinder;
import ru.vat78.homeMoney.model.CommonEntry;
import ru.vat78.homeMoney.model.Defenitions;
import ru.vat78.homeMoney.model.dictionaries.Dictionary;
import ru.vat78.homeMoney.model.exceptions.DbOperationException;
import ru.vat78.homeMoney.model.exceptions.ValidationException;
import ru.vat78.homeMoney.model.exceptions.WrongTypeException;
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

    static void checkUniqueName(@NotNull CommonService service, @NotNull Dictionary entity) throws WrongTypeException, ValidationException{

        String objectType = entity.getType();
        CommonEntry inDB = service.getRecordByName(objectType, entity.getName());
        if (inDB != null && !inDB.getId().equals(entity.getId())){
            throw new ValidationException(Defenitions.FIELDS.NAME, "edit.error.nameExists");
        }
    }

    static void saveEntityToDb(@NotNull CommonService service, @NotNull CommonEntry entity) throws ValidationException {

        if (!service.saveRecord(entity)) {
            throw new ValidationException(Defenitions.FIELDS.NAME, "edit.error.unknown");
        }
    }

    static void deleteEntry(@NotNull CommonService service, String objectType, Long id) throws WrongTypeException, DbOperationException {

        if (!service.deleteRecordById(objectType, id)) {
            throw new DbOperationException("operation.delete", objectType, id);
        }
    }

    static void validateEntry(@NotNull CommonEntry entity) throws ValidationException {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set result = validator.validate(entity);

        if (result.size() > 0) throw new ValidationException(result);
    }

    static CommonEntry buildEntryFromParams(@NotNull CommonEntry entry, @NotNull Map<String,String> params) {

        WebDataBinder binder = new WebDataBinder(entry);
        binder.bind(new MutablePropertyValues(params));

        return entry;
    }

    static Long parseId(String id) {

        Long result = 0L;

        try {
            result = Long.parseLong(id);
        } catch (Exception ignored) {}

        return result;
    }
}
