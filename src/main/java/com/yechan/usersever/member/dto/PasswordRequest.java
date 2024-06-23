package com.yechan.usersever.member.dto;

import com.yechan.usersever.member.validation.Password;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PasswordRequest {

    @NotNull
    Long memberId;

    @Password
    String password;
}
