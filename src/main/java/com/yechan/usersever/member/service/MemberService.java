package com.yechan.usersever.member.service;

import static com.yechan.usersever.member.validation.MemberValidation.checkMember;
import static com.yechan.usersever.member.validation.MemberValidation.duplicateMemberId;

import com.yechan.usersever.common.exception.MemberErrorCode;
import com.yechan.usersever.common.exception.MemberException;
import com.yechan.usersever.member.domain.Member;
import com.yechan.usersever.member.dto.AddressAndPhoneRequest;
import com.yechan.usersever.member.dto.LoginRequest;
import com.yechan.usersever.member.dto.MemberDto;
import com.yechan.usersever.member.dto.MemberRequest;
import com.yechan.usersever.member.dto.PasswordRequest;
import com.yechan.usersever.member.repository.MemberRepository;
import com.yechan.usersever.member.utils.AES;
import java.util.Optional;
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
        checkDuplicationMemberId(memberRequest.getUserId());
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

    public void updatePassword(PasswordRequest passwordRequest) {
        Optional<Member> optionalMember = memberRepository.findById(passwordRequest.getMemberId());

        if (!optionalMember.isPresent()) {
            throw new MemberException(MemberErrorCode.NOT_EXIST_MEMBER);
        }

        Member member = optionalMember.get();
        String beforePassword = member.getPassword();
        checkEqulePassword(passwordRequest.getPassword(), beforePassword);
        member.updatePassword(passwordRequest.getPassword());
        memberRepository.save(member);
    }


    public void updateAddressAndPhone(AddressAndPhoneRequest request) {
        Optional<Member> optionalMember = memberRepository.findById(request.getMemberId());

        if (!optionalMember.isPresent()) {
            throw new MemberException(MemberErrorCode.NOT_EXIST_MEMBER);
        }

        Member member = optionalMember.get();
        member.updateInfo(request.getAddress(), request.getPhone());

        memberRepository.save(member);
    }

    private void checkEqulePassword(String password, String beforePassword) {
        if (passwordEncoder.matches(password, beforePassword)) {
            throw new MemberException(MemberErrorCode.EQULE_PASSWORD);
        }
    }

    public void checkDuplicationMemberId(String memberId) {
        String encodingId = encodeAES(memberId);
        Long existMemberId = memberRepository.findOneByMemberId(encodingId);
        duplicateMemberId(existMemberId);
    }

}
