package com.yechan.usersever.member.service;

import com.yechan.usersever.member.common.exception.MemberErrorCode;
import com.yechan.usersever.member.common.exception.MemberException;
import com.yechan.usersever.member.domain.Member;
import com.yechan.usersever.member.dto.MemberRequest;
import com.yechan.usersever.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(MemberRequest request) throws Exception {

        Optional<Long> existMember = memberRepository.findOneByMemberId(request.getUserId());
        if (existMember.isPresent()) {
            throw new MemberException(MemberErrorCode.DUPLICATE_MEMBER);
        }

        request.convertToHashing(passwordEncoder); // 유저 정보 해싱
        Member member = Member.from(request);
        memberRepository.save(member);
    }
}
