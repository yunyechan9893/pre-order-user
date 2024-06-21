package com.yechan.usersever.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yechan.usersever.member.domain.QMember;
import com.yechan.usersever.member.dto.MemberDto;
import com.yechan.usersever.member.dto.QMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberQueryDslRepository {

    private static QMember member = QMember.member;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findOneByMemberId(String userId) {
        return jpaQueryFactory.select(member.id)
            .from(member)
            .where(member.userId.eq(userId))
            .fetchFirst();
    }

    @Override
    public MemberDto findOneByMemberIdAndPassword(String userId) {
        return jpaQueryFactory.select(
                new QMemberDto(
                    member.userId,
                    member.password))
            .from(member)
            .where(member.userId.eq(userId))
            .fetchFirst();
    }
}
