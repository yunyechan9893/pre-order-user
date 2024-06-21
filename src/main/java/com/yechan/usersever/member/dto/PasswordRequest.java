package com.yechan.usersever.member.dto;

import lombok.Getter;

@Getter
public class PasswordRequest {

    Long memberId;
    String password;
}
