package com.yechan.usersever.member.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CertificationNumberImpl implements ConstraintValidator<CertificationNumber, String> {

    private static final int SIX = 6;

    @Override
    public void initialize(CertificationNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.length() == SIX;
    }
}
