package com.yechan.usersever.member.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode {

    INTERNAL_SERVER_ERROR(-999, "서버 내부 에러입니다", HttpStatus.INTERNAL_SERVER_ERROR),
    DUPLICATE_MEMBER(-1, "중복된 아이디입니다", HttpStatus.INTERNAL_SERVER_ERROR),
    DECRYPTION_FAIL(-2, "암호화 혹은 복호화 실패", HttpStatus.INTERNAL_SERVER_ERROR),
    INITIALIZE_CHIPHER(-3, "암호화 초기화 실패", HttpStatus.INTERNAL_SERVER_ERROR),
    EMPTY_MEMBER(-2, "아이디/비밀번호를 확인해주세요", HttpStatus.INTERNAL_SERVER_ERROR);
    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    public static MemberErrorCode of(int code) {
        return Stream.of(MemberErrorCode.values())
            .filter(message -> message.getCode() == code)
            .findFirst()
            .orElseThrow(MemberException::new);
    }

    public static MemberErrorCode of(String message) {
        return Stream.of(MemberErrorCode.values())
            .filter(error -> error.getMessage()
                .equals(message))
            .findFirst()
            .orElseThrow(MemberException::new);
    }


}