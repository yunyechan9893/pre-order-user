package com.yechan.usersever.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
public class MemberRequest {

    private String userId;
    private String password;
    private String email;
    private String phone;
    private String address;

    public void convertToHashing(PasswordEncoder passwordEncoder) {
        this.userId = passwordEncoder.encode(this.userId);
        this.password = passwordEncoder.encode(this.password);
        this.email = passwordEncoder.encode(this.email);
        this.phone = passwordEncoder.encode(this.phone);
        this.address = passwordEncoder.encode(this.address);
    }

}
