package com.yechan.usersever.member.dto;

import lombok.Getter;

@Getter
public class AddressAndPhoneRequest {

    Long memberId;
    String address;
    String phone;
}
