package com.example.AgenciaTurismo.exceptions.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.time.LocalDate;

public class IsBeforeValidation implements ConstraintValidator <IsBeforeAnotation, LocalDate>{
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }

    @Override
    public void initialize(IsBeforeAnotation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
