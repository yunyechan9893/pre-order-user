package com.yechan.usersever.member.validation;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = PasswordImpl.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "비밀번호는 10자 이상 30자 미만이어야 합니다. 또한 특수문자, 영문 소문자, 숫자가 한자 이상 포함되어야합니다.";
}
