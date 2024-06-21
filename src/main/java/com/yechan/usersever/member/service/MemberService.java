package com.yechan.usersever.member.service;

import static com.yechan.usersever.member.validation.MemberValidation.checkMember;
import static com.yechan.usersever.member.validation.MemberValidation.duplicateMemberId;

import com.yechan.usersever.member.domain.Member;
import com.yechan.usersever.member.dto.LoginRequest;
import com.yechan.usersever.member.dto.MemberDto;
import com.yechan.usersever.member.dto.MemberRequest;
import com.yechan.usersever.member.repository.MemberRepository;
import com.yechan.usersever.member.utils.AES;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AES aes; // 암호화 방식

    public void signup(MemberRequest memberRequest) {
        memberRequest.convertToHashing(passwordEncoder, aes); // 유저 정보 해싱
        Long existMemberId = memberRepository.findOneByMemberId(memberRequest.getUserId());
        duplicateMemberId(existMemberId);
        memberRepository.save(Member.from(memberRequest));
    }

    public void login(LoginRequest loginRequest) {
        String encryptionId = encodeAES(loginRequest.getMemberId());

        MemberDto member = memberRepository.findOneByMemberIdAndPassword(encryptionId);
        Boolean isPassword = passwordEncoder.matches(loginRequest.getPassword(),
            member.getPassword());

        checkMember(encryptionId, isPassword);
    }

    private String encodeAES(String text) {
        return aes.encode(text);
    }

}
