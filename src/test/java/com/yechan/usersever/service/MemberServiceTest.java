package com.yechan.usersever.service;

import org.springframework.boot.test.context.SpringBootTest;
/*
* 시나리오
*
* 사용자가 서비스를 이용하기 위해 회원가입을 시도했다
* 하지만 올바르지 않은 정보들을 나열하여 첫 회원가입은 실패했다
* 다시 올바른 정보를 입력 후 회원가입에 시도했지만 이메일 인증을 하지 않아 실패했다
* 세번째 시도는 아이디가 중복되어 회원가입에 실패했다
* 네번째 시도는 모든 것을 올바르게 작성하여 가입에 성공했다
*
* 이제 가입에 성공했으니 로그인을 하려고 한다
* 하지만 이전 로그인 했던 비밀번호가 생각나지 않아 올바르지 않은 비밀번호를 입력하게 됐다 그래서 실패했다
* 두번째는 모든 것을 올바르게 작성하여 로그인에 성공하게 됐다
*
* */
@SpringBootTest
public class MemberServiceTest {

}
