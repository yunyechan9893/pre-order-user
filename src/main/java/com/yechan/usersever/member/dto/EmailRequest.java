package com.yechan.usersever.member.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class EmailRequest {

    @Email
    String email;
}
