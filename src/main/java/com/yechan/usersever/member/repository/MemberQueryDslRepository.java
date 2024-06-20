package com.yechan.usersever.member.repository;

import java.util.Optional;

public interface MemberQueryDslRepository {

    Optional<Long> findOneByMemberId(String userId);
}
