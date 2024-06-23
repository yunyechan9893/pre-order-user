package com.yechan.usersever.member.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordImpl implements ConstraintValidator<Password, String> {

    private static final String SPECIAL_CHARACTER_PATTERNS = "[!@#$%^&*(),.?\":{}|<>]";
    private static final String UPPERCASE_PATTERNS = "[A-Z]";
    private static final String LOWERCASE_PATTERNS = "[a-z]";
    private static final String NUMERICAL_PATTERNS = "\\d";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        Pattern specialCharPattern = Pattern.compile(SPECIAL_CHARACTER_PATTERNS);
        Pattern englishUpperCharPattern = Pattern.compile(UPPERCASE_PATTERNS);
        Pattern englishLowerCharPattern = Pattern.compile(LOWERCASE_PATTERNS);
        Pattern numericCharPattern = Pattern.compile(NUMERICAL_PATTERNS);

        return value != null &&
            !value.isEmpty() &&
            specialCharPattern.matcher(value).find() &&
            (englishUpperCharPattern.matcher(value).find() ||
                englishLowerCharPattern.matcher(value).find()) &&
            numericCharPattern.matcher(value).find();
    }
}
