package com.auth.mfa.exception.annotation;

import com.auth.mfa.exception.Validator;
import com.auth.mfa.persistence.payload.request.DTORequestRole;
import com.auth.mfa.service.ServiceRole;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueNameRole.ValidatorUniqueNameRole.class })
@Documented
public @interface UniqueNameRole {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String label();

    class ValidatorUniqueNameRole implements ConstraintValidator<UniqueNameRole, DTORequestRole> {

        private String values;
        @Autowired
        private ServiceRole serviceRole;

        @Override
        public void initialize(UniqueNameRole constraintAnnotation) {
            this.values = constraintAnnotation.label();
        }
        @Override
        public boolean isValid(DTORequestRole value, ConstraintValidatorContext context) {
            return !Validator.isNull(value.getName()) && !serviceRole.existsByName(value.getName()) ||
                    !Validator.isNull(value.getName()) && !Validator.isNull(value.getId()) && !serviceRole.existsByNameAndIdNot(value.getName(), value.getId());
        }
    }
}