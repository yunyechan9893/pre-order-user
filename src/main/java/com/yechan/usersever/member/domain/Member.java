package com.yechan.usersever.member.domain;

import com.yechan.usersever.member.dto.MemberRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "member")
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    private String password;

    private String email;

    private String phone;

    private String address;

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateInfo(String phone, String address) {
        this.phone = phone;
        this.address = address;
    }

    public static Member from(MemberRequest member) {
        return Member.builder()
            .userId(member.getUserId())
            .password(member.getPassword())
            .address(member.getAddress())
            .email(member.getEmail())
            .phone(member.getPhone())
            .build();
    }
}
