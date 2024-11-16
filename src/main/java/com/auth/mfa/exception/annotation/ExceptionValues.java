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
@Constraint(validatedBy = { ExceptionValues.ValidatorExceptionValues.class })
@Documented
public @interface ExceptionValues {

    String message() default "{not.valid}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String[] value() default {};

    class ValidatorExceptionValues implements ConstraintValidator<ExceptionValues, String> {

        private String[] values;

        @Override
        public void initialize(ExceptionValues constraintAnnotation) {
            this.values = constraintAnnotation.value();
        }
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return Objects.isNull(value) || Arrays.asList(values).contains(value);
        }
    }
}