package com.yechan.usersever.member.controller;

import com.yechan.usersever.common.dto.CommonResult;
import com.yechan.usersever.common.service.ResponseService;
import com.yechan.usersever.member.dto.AddressAndPhoneRequest;
import com.yechan.usersever.member.dto.EmailAuthenticationRequest;
import com.yechan.usersever.member.dto.EmailRequest;
import com.yechan.usersever.member.dto.LoginRequest;
import com.yechan.usersever.member.dto.MemberRequest;
import com.yechan.usersever.member.dto.PasswordRequest;
import com.yechan.usersever.member.service.MemberService;
import com.yechan.usersever.member.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ResponseService responseService;

    @PostMapping("/signup")
    public CommonResult signup(
        @RequestBody
        MemberRequest memberRequest
    ) {
        memberService.signup(memberRequest);

        return responseService.getSuccessResult();
    }

    @PostMapping("/login")
    public CommonResult login(
        @RequestBody
        LoginRequest loginRequest
    ) {
        memberService.login(loginRequest);

        return responseService.getSuccessResult();
    }

    // 핸드폰 번호, 주소
    @PatchMapping("/info")
    public CommonResult updateUserInfo(
        @RequestBody
        AddressAndPhoneRequest request
    ) {
        memberService.updateAddressAndPhone(request);

        return responseService.getSuccessResult();
    }

    // 비밀번호 변경
    @PatchMapping("/password")
    public CommonResult updatePassword(
        @RequestBody
        PasswordRequest request
    ) {
        memberService.updatePassword(request);

        return responseService.getSuccessResult();
    }

    // 아이디 중복 검사
    @GetMapping("/duplication")
    public CommonResult duplicatedMemberId(
        @RequestParam
        String memberId
    ) {
        memberService.checkDuplicationMemberId(memberId);

        return responseService.getSuccessResult();
    }

    // 이메일 인증
    @PostMapping("/mail")
    public CommonResult sendMail(
        @RequestBody
        EmailRequest request
    ) {
        memberService.sendMail(request);

        return responseService.getSuccessResult();
    }

    @PostMapping("/mail/certification")
    public CommonResult verifyAuthenticationNumber(
        @RequestBody
        EmailAuthenticationRequest request
    ) {
        String authNumber = memberService.verifyEmail(request);

        return responseService.getSingleResult(authNumber);
    }

    // 핸드폰 인증 (추후 시간날 때 진행)
}
