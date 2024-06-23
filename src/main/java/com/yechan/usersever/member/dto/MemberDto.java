package com.yechan.usersever.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.yechan.usersever.member.validation.Password;
import com.yechan.usersever.member.validation.Phone;
import com.yechan.usersever.member.validation.UserId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberDto {

    private Long id;
    private String userId;
    private String password;
    private String email;
    private String phone;
    private String address;

    /*
    * 로그인 할 때 활용
    * */
    @QueryProjection
    public MemberDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
