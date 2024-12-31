package com.se100.clinic_management.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

import com.se100.clinic_management.annotations.EnumValidator;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

    private Enum<?>[] enumValues;

    @Override
    public void initialize(EnumValidator annotation) {
        enumValues = annotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return Arrays.stream(enumValues).anyMatch(e -> e.name().equals(value));
    }
}