package com.yechan.usersever.member.controller;

import com.yechan.usersever.member.common.dto.CommonResult;
import com.yechan.usersever.member.common.service.ResponseService;
import com.yechan.usersever.member.dto.MemberRequest;
import com.yechan.usersever.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ResponseService responseService;

    @PostMapping("/signup")
    public CommonResult signup(
        @RequestBody
        MemberRequest memberRequest) throws Exception {
        memberService.signup(memberRequest);
        return responseService.getSuccessResult();
    }
}
