package com.yechan.usersever.common.exception;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {

  private MemberErrorCode memberErrorCode = MemberErrorCode.DUPLICATE_MEMBER;

  public MemberException() {
    super("error");
  }

  public MemberException(String message) {
    super(message);
  }

  public MemberException(MemberErrorCode memberErrorCode) {
    super(memberErrorCode.getMessage());
    this.memberErrorCode = memberErrorCode;
  }


  public MemberException(Throwable cause) {
    super(cause);
  }

  // 문자열 가급적 쓰지말자
  @Deprecated
  public MemberException(String message, Throwable cause) {
    super(message, cause);
  }

}
