package com.yechan.usersever.member.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.regex.Pattern;

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
