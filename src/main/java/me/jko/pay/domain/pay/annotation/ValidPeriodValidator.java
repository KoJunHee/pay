package me.jko.pay.domain.pay.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidPeriodValidator implements ConstraintValidator<ValidPeriod, String> {
    @Override
    public void initialize(ValidPeriod constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        int month = Integer.parseInt(value.substring(0, 2));
        return month >= 1 && month <= 12;
    }
}
