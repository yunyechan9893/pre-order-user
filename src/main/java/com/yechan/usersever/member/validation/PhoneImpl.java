package com.yechan.usersever.member.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneImpl implements ConstraintValidator<Phone, String> {

    private String pattern;

    @Override
    public void initialize(Phone constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        // 정규식 패턴을 사용하여 유효성 검사
        return Pattern.matches(pattern, value);
    }
}
