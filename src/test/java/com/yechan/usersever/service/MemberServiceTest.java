package com.yechan.usersever.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.yechan.usersever.common.exception.MemberException;
import com.yechan.usersever.member.domain.Member;
import com.yechan.usersever.member.dto.AddressAndPhoneRequest;
import com.yechan.usersever.member.dto.EmailAuthenticationRequest;
import com.yechan.usersever.member.dto.EmailRequest;
import com.yechan.usersever.member.dto.LoginRequest;
import com.yechan.usersever.member.dto.MemberRequest;
import com.yechan.usersever.member.dto.PasswordRequest;
import com.yechan.usersever.member.repository.MemberRepository;
import com.yechan.usersever.member.service.MemberService;
import com.yechan.usersever.member.service.RedisService;
import com.yechan.usersever.member.utils.AES;
import java.time.Duration;
import java.util.Optional;
import javax.mail.internet.MimeMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class MemberServiceTest {

    private static final String CHECK_COMPLETED = "TRUE";
    private static final Duration TEN_MINUTES = Duration.ofMillis(1000L * 60L * 10L);
    private static final Duration THREE_MINUTES = Duration.ofMillis(1000L * 60L * 3L);
    private static final String AUTH_NUMBER = "000000";
    private static final String EMAIL = "TEST@XXXX.com";
    private static final String MEMBER_ID = "testMemberId";
    private static final String PASSWORD = "!aaaa12345";
    private static final String ADDRESS = "xxxx";
    private static final String PHONE = "00000000000";
    @Autowired
    RedisService redisService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    AES aes;
    @MockBean
    MimeMessage mimeMessage;

    @AfterEach
    void rollback() {
        memberRepository.deleteAll();
        redisService.deleteValues(EMAIL);
        redisService.deleteValues(AUTH_NUMBER);
    }

    @Test
    void singup() {
        MemberRequest memberRequest = new MemberRequest(
            MEMBER_ID,
            PASSWORD,
            EMAIL,
            PHONE,
            ADDRESS,
            AUTH_NUMBER
        );

        redisService.setValues(AUTH_NUMBER, CHECK_COMPLETED, TEN_MINUTES);
        memberService.signup(memberRequest);

        assertThat(memberRepository.findOneByMemberId(
            aes.encode(MEMBER_ID))).isNotNull();
    }

    @Nested
    @DisplayName("checkDuplicationMemberId 메서드는")
    class CheckDuplicationMemberId {

        @Test
        @DisplayName("유효한 아이디로 중복 확인을 통과할 수 있다")
        void notDuplicateMemberId() {
            assertThatCode(() -> memberService.checkDuplicationMemberId(MEMBER_ID))
                .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("중복된 아이디를 찾아낼 수 있다")
        void duplicateMemberId() {
            singup();

            assertThatThrownBy(() -> memberService.checkDuplicationMemberId(MEMBER_ID))
                .isInstanceOf(MemberException.class);
        }
    }

    @Nested
    @DisplayName("sendMail 메서드는")
    class SendMail {

        @Test
        @DisplayName("유효한 아이디로 중복 확인을 통과할 수 있다")
        void notDuplicateMemberId() {
            EmailRequest emailRequest = new EmailRequest(EMAIL);
            assertThatCode(() -> memberService.sendMail(emailRequest))
                .doesNotThrowAnyException();

            assertThat(redisService.getValues(EMAIL)).isNotEqualTo("null");
        }
    }

    @Test
    @DisplayName("verifyEmail 메서드는 ")
    void verifyEmail() {
        EmailAuthenticationRequest request = new EmailAuthenticationRequest(
            EMAIL,
            AUTH_NUMBER
        );

        redisService.setValues(EMAIL, AUTH_NUMBER, THREE_MINUTES);
        String authNumber = memberService.verifyEmail(request);

        assertThat(authNumber).isEqualTo(AUTH_NUMBER);
    }

    @Nested
    @DisplayName("login 메서드는")
    class Login {

        @Test
        @DisplayName("아이디가 일치하지 않는다면 에러를 시킨다")
        void notEquleMemberId() {
            LoginRequest loginRequest = new LoginRequest(
                MEMBER_ID,
                PASSWORD
            );

            assertThatThrownBy(() -> memberService.login(loginRequest))
                .isInstanceOf(MemberException.class);
        }

        @Test
        @DisplayName("비밀번호가 일치하지 않는다면 에러를 시킨다")
        void notEqulePassword() {
            singup();

            String diffrentPassword = PASSWORD + "a";
            LoginRequest loginRequest = new LoginRequest(
                MEMBER_ID,
                diffrentPassword
            );

            assertThatThrownBy(() -> memberService.login(loginRequest))
                .isInstanceOf(MemberException.class);
        }

        @Test
        @DisplayName("아이디 혹은 비밀번호가 일치하면 로그인을 할 수 있다")
        void login() {
            singup();

            LoginRequest loginRequest = new LoginRequest(
                MEMBER_ID,
                PASSWORD
            );

            assertThatCode(() -> memberService.login(loginRequest))
                .doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("updatePassword 메서드는")
    class UpdatePassword {

        @Test
        @DisplayName("비밀번호를 업데이트 할 수 있다")
        void notEquleMemberId() {
            singup();
            Long id = memberRepository.findOneByMemberId(aes.encode(MEMBER_ID));

            String newPassword = PASSWORD + "a";
            PasswordRequest passwordRequest = new PasswordRequest(
                id,
                newPassword
            );

            assertThatCode(() -> memberService.updatePassword(passwordRequest))
                .doesNotThrowAnyException();

            Optional<Member> member = memberRepository.findById(id);
            assertThat(member.get().getPassword()).isEqualTo(newPassword);
        }

        @Test
        @DisplayName("이전 비밀번호와 일치한다면 에러를 시킨다")
        void notEqulePassword() {
            singup();
            Long id = memberRepository.findOneByMemberId(aes.encode(MEMBER_ID));

            PasswordRequest passwordRequest = new PasswordRequest(
                id,
                PASSWORD
            );

            assertThatThrownBy(() -> memberService.updatePassword(passwordRequest))
                .isInstanceOf(MemberException.class);
        }
    }

    @Nested
    @DisplayName("updateAddressAndPhone 메서드는")
    class UpdateAddressAndPhone {

        @Test
        @DisplayName("비밀번호를 업데이트 할 수 있다")
        void notEquleMemberId() {
            singup();
            Long id = memberRepository.findOneByMemberId(aes.encode(MEMBER_ID));

            String newAddress = "yyyy";
            String newPhone = "11111111111";
            AddressAndPhoneRequest request = new AddressAndPhoneRequest(
                id,
                newPhone,
                newAddress
            );

            assertThatCode(() -> memberService.updateAddressAndPhone(request))
                .doesNotThrowAnyException();

            Optional<Member> member = memberRepository.findById(id);
            assertThat(member.get().getAddress()).isEqualTo(newAddress);
            assertThat(member.get().getPhone()).isEqualTo(newPhone);
        }
    }
}
