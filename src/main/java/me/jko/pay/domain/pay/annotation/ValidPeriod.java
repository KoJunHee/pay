package me.jko.pay.domain.pay.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = ValidPeriodValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPeriod {

    String message() default "Invalid Period";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}




