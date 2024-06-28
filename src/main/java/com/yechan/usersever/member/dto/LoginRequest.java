package com.yechan.usersever.member.dto;

import com.yechan.usersever.member.validation.Password;
import com.yechan.usersever.member.validation.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {

    @UserId
    String userId;

    @Password
    String password;
}
