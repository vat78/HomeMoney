package ru.vat78.homeMoney.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface UIDef {

    public static final String TEXT = "text";
    public static final String CHECKBOX = "checkbox";
    public static final String SELECT = "select";
    public static final String DATE = "date";
    public static final String NUMERIC = "numeric";
    public static final String TYPEAHEAD = "typeahead";

    String caption() default "";
    boolean shown() default false;
    boolean editable() default false;
    int order() default 0;
    String control() default TEXT;
    String source() default "";

}
