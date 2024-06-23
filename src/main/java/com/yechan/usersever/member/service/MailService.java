package com.yechan.usersever.member.service;

import static com.yechan.usersever.common.exception.MemberErrorCode.MESSAGE_SETTING_FALI;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.yechan.usersever.common.exception.MemberException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private static final Integer SIX_DIGITS = 6;
    private static final String TITLE = "회원 가입 인증 이메일 입니다.";

    private final JavaMailSender mailSender;

    @Value("${enviroment.mail.id}")
    private String from;

    public String send(String authNumber, String toMail) {
        String content = getContentFormat(authNumber);

        MimeMessage message = mailSender.createMimeMessage();//JavaMailSender 객체를 사용하여 MimeMessage 객체를 생성
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8.name());
            helper.setFrom(from);//이메일의 발신자 주소 설정
            helper.setTo(toMail);//이메일의 수신자 주소 설정
            helper.setSubject(TITLE);//이메일의 제목을 설정
            helper.setText(content, true);//이메일의 내용 설정 두 번째 매개 변수에 true를 설정하여 html 설정으로한다.
            mailSender.send(message);

            return authNumber;
        } catch (MessagingException e) {
            throw new MemberException();
        }

    }

    String getAuthNumber() {

        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder authNumber = new StringBuilder();
            for (int i = 0; i < SIX_DIGITS; i++) {
                authNumber.append(random.nextInt(10));
            }

            return authNumber.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new MemberException(MESSAGE_SETTING_FALI);
        }

    }

    private String getContentFormat(String authNumber) {
        return "나의 APP을 방문해주셔서 감사합니다." +    //html 형식으로 작성 !
            "<br><br>" +
            "인증 번호는 " + authNumber + "입니다." +
            "<br>" +
            "인증번호를 제대로 입력해주세요";
    }

}
