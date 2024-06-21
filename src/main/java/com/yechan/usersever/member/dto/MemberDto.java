package com.yechan.usersever.member.dto;

import com.querydsl.core.annotations.QueryProjection;
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
