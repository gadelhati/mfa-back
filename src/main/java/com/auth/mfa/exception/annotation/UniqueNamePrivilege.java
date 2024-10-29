package com.auth.mfa.exception.annotation;

import com.auth.mfa.exception.Validator;
import com.auth.mfa.persistence.payload.request.DTORequestPrivilege;
import com.auth.mfa.service.ServicePrivilege;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueNamePrivilege.ValidatorUniqueNamePrivilege.class })
@Documented
public @interface UniqueNamePrivilege {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    class ValidatorUniqueNamePrivilege implements ConstraintValidator<UniqueNamePrivilege, DTORequestPrivilege> {

        @Autowired
        private ServicePrivilege servicePrivilege;

        @Override
        public boolean isValid(DTORequestPrivilege value, ConstraintValidatorContext context) {
            return !Validator.isNull(value.getName()) && !servicePrivilege.existsByName(value.getName()) ||
                    !Validator.isNull(value.getName()) && !Validator.isNull(value.getId()) && !servicePrivilege.existsByNameAndIdNot(value.getName(), value.getId());
        }
    }
}