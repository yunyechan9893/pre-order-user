package com.yechan.usersever.common.exception;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private static final String EMPTY = "";
    private String field;
    private String message;
    private Integer errorCode;

    public static ErrorResponse of(String field, String message) {
        MemberErrorCode memberErrorCode = MemberErrorCode.of(message);

        if (Objects.isNull(memberErrorCode.getCode())) {
            return empty();
        }

        return ErrorResponse.builder()
            .field(field)
            .message(message)
            .errorCode(memberErrorCode.getCode())
            .build();
    }

    public static ErrorResponse of(String message) {
        return ErrorResponse.builder()
            .message(message)
            .errorCode(MemberErrorCode.BAD_REQUEST.getCode())
            .build();
    }



    private static ErrorResponse empty() {
        MemberErrorCode memberError = MemberErrorCode.BAD_REQUEST;

        return ErrorResponse.builder()
            .field(EMPTY)
            .message(memberError.getMessage())
            .errorCode(memberError.getCode())
            .build();
    }
}
