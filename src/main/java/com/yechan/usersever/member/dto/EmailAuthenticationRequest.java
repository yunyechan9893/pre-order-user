package com.yechan.usersever.member.dto;

import com.yechan.usersever.member.validation.CertificationNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class EmailAuthenticationRequest {

    @Email
    private String email;

    @CertificationNumber
    private String certificationNumber;
}
