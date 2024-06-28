package com.yechan.usersever.member.dto;

import com.yechan.usersever.member.validation.CertificationNumber;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailAuthenticationRequest {

    @Email
    private String email;

    @CertificationNumber
    private String certificationNumber;
}
