package com.example.AgenciaTurismo.exceptions.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsBeforeValidation.class)
@Target( { ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsBeforeAnotation {
        String message() default "Invalid date";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
}
