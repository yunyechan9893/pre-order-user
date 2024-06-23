package com.yechan.usersever.member.dto;

import com.yechan.usersever.member.validation.Phone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AddressAndPhoneRequest {

    @NotNull
    Long memberId;

    @Email
    String address;

    @Phone
    String phone;
}
