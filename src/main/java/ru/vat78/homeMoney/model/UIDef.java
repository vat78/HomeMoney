package ru.vat78.homeMoney.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface UIDef {
    String caption() default "";
    boolean shown() default false;
    boolean editable() default false;
    int num() default 0;
    String type() default "text";

}
