package com.yechan.usersever.member.dto;

import lombok.Getter;

@Getter
public class EmailAuthenticationRequest {
    private String email;
    private String certificationNumber;
}
