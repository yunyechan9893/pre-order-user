package com.yechan.usersever.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;

@Configuration
public class NaverMailConfig {

    @Value("${enviroment.mail.id}")
    private String mailId;

    @Value("${enviroment.mail.password}")
    private String mailPassword;


    private static final String SMTP_HOST = "smtp.naver.com";
    private static final Integer SMTP_PORT = 465;

    @Bean
    public JavaMailSender mailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(SMTP_HOST);
        mailSender.setPort(SMTP_PORT);
        mailSender.setUsername(mailId);
        mailSender.setPassword(mailPassword);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.ssl.checkserveridentity", "true");
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.debug", "true");
        javaMailProperties.put("mail.smtp.ssl.trust", SMTP_HOST);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }

}
