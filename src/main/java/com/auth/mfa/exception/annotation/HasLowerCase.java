package com.auth.mfa.exception.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static com.auth.mfa.exception.Validator.hasLowerCase;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { HasLowerCase.ValidatorHasLowerCase.class })
@Documented
public @interface HasLowerCase {

    String message() default "{has.lower.case}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    class ValidatorHasLowerCase implements ConstraintValidator<HasLowerCase, String> {

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return hasLowerCase(value);
        }
    }
}