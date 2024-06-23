package com.yechan.usersever.member.dto;

import com.yechan.usersever.member.utils.AES;
import com.yechan.usersever.member.validation.CertificationNumber;
import com.yechan.usersever.member.validation.Password;
import com.yechan.usersever.member.validation.Phone;
import com.yechan.usersever.member.validation.UserId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
@Getter
@AllArgsConstructor
public class MemberRequest {

    @UserId
    private String userId;

    @Password
    private String password;

    @Email
    private String email;

    @Phone
    private String phone;

    @NotNull
    private String address;

    @CertificationNumber
    private String authNumber;

    public void convertToHashing(PasswordEncoder passwordEncoder, AES aes) {
        this.userId = aes.encode(this.userId);
        this.password = passwordEncoder.encode(this.password);
        this.email = aes.encode(this.email);
        this.phone = aes.encode(this.phone);
        this.address = aes.encode(this.address);
    }

}
