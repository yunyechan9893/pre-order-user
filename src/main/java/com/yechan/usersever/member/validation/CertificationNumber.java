package com.yechan.usersever.member.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = CertificationNumberImpl.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CertificationNumber {

    String message() default "인증번호는 6자입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
