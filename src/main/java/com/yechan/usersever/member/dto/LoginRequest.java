package com.yechan.usersever.member.dto;

import com.yechan.usersever.member.validation.Password;
import com.yechan.usersever.member.validation.UserId;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {

    @UserId
    String memberId;

    @Password
    String password;
}
