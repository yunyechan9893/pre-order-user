package com.yechan.usersever.member.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yechan.usersever.common.service.ResponseService;
import com.yechan.usersever.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({MemberController.class, ResponseService.class})
public class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Nested
    @DisplayName("signup 메서드는")
    class Signup {

        @Test
        @DisplayName("유효한 정보로 회원가입을 할 수 있다")
        void success() throws Exception {

            String requestJson = "{"
                + "\"userId\":\"testUserId\","
                + "\"password\":\"testPassword!1\","
                + "\"email\":\"testEmail@xxxxx.com\","
                + "\"phone\":\"01000000000\","
                + "\"address\":\"TestAddress\","
                + "\"authNumber\":\"000000\""
                + "}";

            mockMvc.perform(post("/member/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
        }

        @Test
        @DisplayName("유효하지 않은 정보로 회원가입을 할 수 없다")
        void fail() throws Exception {

            String requestJson = "{"
                + "\"userId\":\"test\","
                + "\"password\":\"tes\","
                + "\"email\":\"test\","
                + "\"phone\":\"010\","
                + "\"address\":\"Test\","
                + "\"authNumber\":\"00\""
                + "}";

            mockMvc.perform(post("/member/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("login 메서드는")
    class Login {

        @Test
        @DisplayName("유효한 정보로 로그인 할 수 있다")
        void success() throws Exception {

            String requestJson = "{"
                + "\"userId\":\"testUserId\","
                + "\"password\":\"testPassword!1\""
                + "}";

            mockMvc.perform(post("/member/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
        }

        @Test
        @DisplayName("유효하지 않은 정보로 로그인 할 수 없다")
        void fail() throws Exception {

            String requestJson = "{"
                + "\"userId\":\"test\","
                + "\"password\":\"tes\""
                + "}";

            mockMvc.perform(post("/member/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("updateUserInfo 메서드는")
    class UpdateUserInfo {
        @Test
        @DisplayName("유효한 정보로 비밀번호를 변경할 수 있다")
        void success() throws Exception {

            String requestJson = "{"
                + "\"id\":1,"
                + "\"address\":\"TestAddress\","
                + "\"phone\":\"01000000000\""
                + "}";

            mockMvc.perform(patch("/member/info")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
        }

        @Test
        @DisplayName("유효하지 않은 정보로 회원 정보를 변경할 수 없다")
        void fail() throws Exception {

            String requestJson = "{"
                + "\"id\":1,"
                + "\"phone\":\"010\","
                + "\"address\":\"Test\""
                + "}";

            mockMvc.perform(patch("/member/info")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("duplicatedMemberId 메서드는")
    class DuplicatedMemberId {

        @Test
        @DisplayName("유효한 아이디로 중복 확인이 가능하다")
        void success() throws Exception {

            mockMvc.perform(get("/member/duplication?userId=testUserId"))
                .andDo(print())
                .andExpect(status().isOk());
        }

        @Test
        @DisplayName("유효하지 아이디로 중복 확인할 수 없다")
        void fail() throws Exception {

            mockMvc.perform(get("/member/duplication?userId=test"))
                .andDo(print())
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("sendMail 메서드는")
    class SendMail {

        @Test
        @DisplayName("유효한 이메일로 메세지를 전송할 수 있다")
        void success() throws Exception {

            String requestJson = "{"
                + "\"email\":\"test@xxxxx.com\""
                + "}";

            mockMvc.perform(post("/member/mail")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
        }

        @Test
        @DisplayName("유효하지 않은 이메일로 메세지를 전송할 수 없다")
        void fail() throws Exception {

            String requestJson = "{"
                + "\"email\":\"testxxxxx.com\""
                + "}";

            mockMvc.perform(post("/member/mail")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("verifyEmail 메서드는")
    class VerifyEmail {

        @Test
        @DisplayName("유효한 데이터로 이메일 인증을 할 수 있다")
        void success() throws Exception {

            String requestJson = "{"
                + "\"email\":\"test@xxxxx.com\","
                + "\"certificationNumber\":\"000000\""
                + "}";

            mockMvc.perform(post("/member/mail/certification")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
        }

        @Test
        @DisplayName("유효하지 데이터로 이메일 인증을 할 수 없다")
        void fail() throws Exception {

            String requestJson = "{"
                + "\"email\":\"testxxxxx.com\","
                + "\"certificationNumber\":\"0000\""
                + "}";

            mockMvc.perform(post("/member/mail")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
        }
    }
}
