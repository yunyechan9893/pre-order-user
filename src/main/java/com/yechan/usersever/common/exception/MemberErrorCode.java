package com.yechan.usersever.common.exception;

import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode {

    INTERNAL_SERVER_ERROR(-999, "서버 내부 에러입니다", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(-998, "클라이언트 에러입니다", HttpStatus.BAD_REQUEST),
    DUPLICATE_MEMBER(-1, "중복된 아이디입니다", HttpStatus.INTERNAL_SERVER_ERROR),
    DECRYPTION_FAIL(-2, "암호화 혹은 복호화 실패", HttpStatus.INTERNAL_SERVER_ERROR),
    INITIALIZE_CHIPHER(-3, "암호화 초기화 실패", HttpStatus.INTERNAL_SERVER_ERROR),
    EMPTY_MEMBER(-4, "아이디/비밀번호를 확인해주세요", HttpStatus.INTERNAL_SERVER_ERROR),
    EQULE_PASSWORD(-5, "현재 설정된 비밀번호 입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_EXIST_MEMBER(-6, "존재하지 않는 멤버입니다", HttpStatus.INTERNAL_SERVER_ERROR),
    MESSAGE_SETTING_FALI(-7, "메세지 세팅에 실패했습니다", HttpStatus.INTERNAL_SERVER_ERROR),
    NO_SUCH_ALGORITHM(-8, "랜덤 알고리즘을 찾을 수 없습니다", HttpStatus.INTERNAL_SERVER_ERROR),
    DIFFERENT_AUTH_NUMBER(-9, "인증번호가 다릅니다", HttpStatus.INTERNAL_SERVER_ERROR),
    CHECK_EMAIL_AUTHENTICATION(-10, "이메일 인증을 다시 해주세요", HttpStatus.INTERNAL_SERVER_ERROR),
    DUPLICATION_EMAIL(-11, "이메일 인증을 다시 해주세요", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_AUTH_NUMBER(-12, "인증번호는 6자입니다.", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(-13, "must be a well-formed email address", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_NUMBER(-14, "핸드폰은 11자 미만이어야합니다.", HttpStatus.BAD_REQUEST),
    INVALID_USERID(-15, "유저 아이디는 5자 이상 30자 미만이어야합니다", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(-16, "비밀번호는 10자 이상 30자 미만이어야 합니다. 또한 특수문자, 영문 소문자, 숫자가 한자 이상 포함되어야합니다.", HttpStatus.BAD_REQUEST),;
    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    public static MemberErrorCode of(int code) {
        return Stream.of(MemberErrorCode.values())
            .filter(message -> message.getCode() == code)
            .findFirst()
            .orElse(INTERNAL_SERVER_ERROR);
    }

    public static MemberErrorCode of(String message) {
        return Stream.of(MemberErrorCode.values())
            .filter(error -> error.getMessage()
                .equals(message))
            .findFirst()
            .orElse(INTERNAL_SERVER_ERROR);
    }


}