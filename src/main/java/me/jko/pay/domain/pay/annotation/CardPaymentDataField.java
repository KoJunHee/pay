package me.jko.pay.domain.pay.annotation;

import me.jko.pay.domain.pay.enums.CardPaymentDataFieldType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CardPaymentDataField {

    String message() default "Invalid Card Payment Data Field";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    CardPaymentDataFieldType cardPaymentDataFieldType();

    int length();
}
