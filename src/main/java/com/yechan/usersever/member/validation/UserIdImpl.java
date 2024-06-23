package com.yechan.usersever.member.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.regex.Pattern;

public class UserIdImpl implements ConstraintValidator<UserId, String> {

    private static final Integer MIN_LENGTH = 5;
    private static final Integer MAX_LENGTH = 30;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.length() > MIN_LENGTH && value.length() < MAX_LENGTH;
    }
}
