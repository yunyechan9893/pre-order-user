package com.yechan.usersever.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yechan.usersever.member.domain.Member;
import com.yechan.usersever.member.domain.QMember;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberQueryDslRepository {

    private static QMember member = QMember.member;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Long> findOneByMemberId(String userId) {
        return jpaQueryFactory.select(member.id)
            .from(member)
            .where(member.userId.eq(userId))
            .stream()
            .findFirst();
    }
}
