package com.yechan.usersever.member.repository;

import com.yechan.usersever.member.dto.MemberDto;

public interface MemberQueryDslRepository {

    Long findOneByMemberId(String userId);
    MemberDto findOneByMemberIdAndPassword(String userId);
    Long findByEmail(String email);
}
