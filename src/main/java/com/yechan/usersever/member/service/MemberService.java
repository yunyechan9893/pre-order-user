package com.yechan.usersever.member.service;

import static com.yechan.usersever.common.exception.MemberErrorCode.CHECK_EMAIL_AUTHENTICATION;
import static com.yechan.usersever.common.exception.MemberErrorCode.DUPLICATION_EMAIL;
import static com.yechan.usersever.common.exception.MemberErrorCode.EQULE_PASSWORD;
import static com.yechan.usersever.member.validation.MemberValidation.checkMember;
import static com.yechan.usersever.member.validation.MemberValidation.checkMemberIdPassword;
import static com.yechan.usersever.member.validation.MemberValidation.duplicateMemberId;
import static com.yechan.usersever.member.validation.MemberValidation.verifyAuthenticationNumber;

import com.yechan.usersever.common.exception.MemberErrorCode;
import com.yechan.usersever.common.exception.MemberException;
import com.yechan.usersever.member.domain.Member;
import com.yechan.usersever.member.dto.AddressAndPhoneRequest;
import com.yechan.usersever.member.dto.EmailAuthenticationRequest;
import com.yechan.usersever.member.dto.EmailRequest;
import com.yechan.usersever.member.dto.LoginRequest;
import com.yechan.usersever.member.dto.MemberDto;
import com.yechan.usersever.member.dto.MemberRequest;
import com.yechan.usersever.member.dto.PasswordRequest;
import com.yechan.usersever.member.repository.MemberRepository;
import com.yechan.usersever.member.utils.AES;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private static final String CHECK_COMPLETED = "TRUE";
    private static final String NULL = "null";
    private static final Duration THREE_MINUTES = Duration.ofMillis(1000L * 60L * 3L);
    private static final Duration TEN_MINUTES = Duration.ofMillis(1000L * 60L * 10L);
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final MailService mailService;
    private final AES aes; // 암호화 방식

    public void signup(MemberRequest memberRequest) {
        String authentication = redisService.getValues(memberRequest.getAuthNumber());
        if (authentication.equals(NULL)) {
            throw new MemberException(CHECK_EMAIL_AUTHENTICATION);
        }

        memberRequest.convertToHashing(passwordEncoder, aes); // 유저 정보 해싱
        checkDuplicationMemberId(memberRequest.getUserId());
        memberRepository.save(Member.from(memberRequest));
    }

    public void login(LoginRequest loginRequest) {
        String encryptionId = encodeAES(loginRequest.getUserId());

        MemberDto member = memberRepository.findOneByMemberIdAndPassword(encryptionId);
        checkMember(member);
        Boolean isPassword = passwordEncoder.matches(loginRequest.getPassword(),
            member.getPassword());
        checkMemberIdPassword(encryptionId, isPassword);
    }

    private String encodeAES(String text) {
        return aes.encode(text);
    }

    public void updatePassword(PasswordRequest passwordRequest) {
        Optional<Member> optionalMember = memberRepository.findById(passwordRequest.getId());

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
        Optional<Member> optionalMember = memberRepository.findById(request.getId());

        if (!optionalMember.isPresent()) {
            throw new MemberException(MemberErrorCode.NOT_EXIST_MEMBER);
        }

        Member member = optionalMember.get();
        member.updateInfo(request.getAddress(), request.getPhone());

        memberRepository.save(member);
    }

    private void checkEqulePassword(String password, String beforePassword) {
        if (passwordEncoder.matches(password, beforePassword)) {
            throw new MemberException(EQULE_PASSWORD);
        }
    }

    public void checkDuplicationMemberId(String memberId) {
        String encodingId = encodeAES(memberId);
        Long existMemberId = memberRepository.findOneByMemberId(encodingId);
        duplicateMemberId(existMemberId);
    }

    public void sendMail(EmailRequest request) {
        Long duplicationEmailId = memberRepository.findByEmail(request.getEmail());

        if (Objects.nonNull(duplicationEmailId)) {
            throw new MemberException(DUPLICATION_EMAIL);
        }

        String authNumber = mailService.getAuthNumber();
        mailService.send(authNumber, request.getEmail());
        redisService.setValues(request.getEmail(), authNumber, THREE_MINUTES);
    }

    public String verifyEmail(EmailAuthenticationRequest request) {
        String authNumber = redisService.getValues(request.getEmail());
        verifyAuthenticationNumber(authNumber, request.getCertificationNumber());
        redisService.deleteValues(request.getEmail());
        redisService.setValues(authNumber, CHECK_COMPLETED, TEN_MINUTES);

        return authNumber;
    }
}
