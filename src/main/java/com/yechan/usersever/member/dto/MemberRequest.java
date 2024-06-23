package com.yechan.usersever.member.dto;

import com.yechan.usersever.member.utils.AES;
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
    private String authNumber;

    public void convertToHashing(PasswordEncoder passwordEncoder, AES aes) {
        this.userId = aes.encode(this.userId);
        this.password = passwordEncoder.encode(this.password);
        this.email = aes.encode(this.email);
        this.phone = aes.encode(this.phone);
        this.address = aes.encode(this.address);
    }

}
