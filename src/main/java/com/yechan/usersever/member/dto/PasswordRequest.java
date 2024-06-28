package com.yechan.usersever.member.dto;

import com.yechan.usersever.member.validation.Password;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordRequest {

    @NotNull
    Long id;

    @Password
    String password;
}
