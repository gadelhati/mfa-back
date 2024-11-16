package com.auth.mfa.exception.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.util.Arrays;
import java.util.Objects;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ExceptionRange.ValidatorExceptionRange.class })
@Documented
public @interface ExceptionRange {

    int[] value() default {};
    String message() default "{not.range}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    class ValidatorExceptionRange implements ConstraintValidator<ExceptionRange, Integer> {

        private int[] values;

        @Override
        public void initialize(ExceptionRange constraintAnnotation) {
            this.values = constraintAnnotation.value();
        }
        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext context) {
            return Objects.isNull(value) || Arrays.stream(values).anyMatch(validValue -> validValue == value);
        }
    }
}