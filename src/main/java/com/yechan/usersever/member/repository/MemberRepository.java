package com.yechan.usersever.member.repository;

import com.yechan.usersever.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryDslRepository {

}
