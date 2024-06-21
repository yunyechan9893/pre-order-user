package com.yechan.usersever.member.controller;

import com.yechan.usersever.common.dto.CommonResult;
import com.yechan.usersever.common.service.ResponseService;
import com.yechan.usersever.member.dto.LoginRequest;
import com.yechan.usersever.member.dto.MemberRequest;
import com.yechan.usersever.member.dto.PasswordRequest;
import com.yechan.usersever.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public void updateUserInfo() {

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
    @PostMapping("/duplication")
    public void duplicatedUserId() {

    }

    // 이메일 인증

    // 핸드폰 인증 (추후 시간날 때 진행)
}
