package com.java5.demoJV5.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java5.demoJV5.jpa.UserJPA;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Properties;

@Service
public class EmailRegisterService {

    @Autowired
    private UserJPA userJPA;

    private static final String CHARACTERS = "0123456789";
    private static final int OTP_LENGTH = 6;

    private String generateOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return otp.toString();
    }

    public void sendOtpEmail(String recipient, HttpServletResponse response) throws MessagingException {
        final String senderEmail = "trankhanhbang.010105@gmail.com";
        final String senderPassword = "uyqo qiay qjhn ndnm";

        if (userJPA.existsByEmail(recipient)) {
            throw new IllegalArgumentException("Email đã tồn tại!");
        }

        String otp = generateOTP();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");

        Session mailSession = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        Message message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject("Xác nhận đăng ký tài khoản");
        message.setText("Mã OTP của bạn là: " + otp);

        Transport.send(message);

        Cookie otpCookie = new Cookie("otpRegister", otp);
        otpCookie.setMaxAge(5 * 60);
        otpCookie.setPath("/");
        response.addCookie(otpCookie);
    }
}
