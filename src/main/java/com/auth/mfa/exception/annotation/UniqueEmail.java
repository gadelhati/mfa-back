package com.auth.mfa.exception.annotation;

import com.auth.mfa.persistence.payload.request.DTORequestUser;
import com.auth.mfa.service.ServiceUser;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

import static com.auth.mfa.exception.Validator.isNull;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueEmail.ValidatorUniqueEmail.class })
@Documented
public @interface UniqueEmail {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    class ValidatorUniqueEmail implements ConstraintValidator<UniqueEmail, DTORequestUser> {

        @Autowired
        private ServiceUser serviceUser;

        @Override
        public boolean isValid(DTORequestUser value, ConstraintValidatorContext context) {
            return !isNull(value.getEmail()) && !serviceUser.existsByEmail(value.getEmail()) ||
                    !isNull(value.getEmail()) && !isNull(value.getId()) && !serviceUser.existsByEmailAndIdNot(value.getEmail(), value.getId());
        }
    }
}