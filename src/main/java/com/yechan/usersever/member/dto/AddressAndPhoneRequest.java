package com.yechan.usersever.member.dto;

import com.yechan.usersever.member.validation.Phone;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressAndPhoneRequest {

    @NotNull
    Long id;

    @NotNull
    String address;

    @Phone
    String phone;
}
