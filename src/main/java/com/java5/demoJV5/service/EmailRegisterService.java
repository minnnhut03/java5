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
	public void sendEmail(String recipient, String subject, String content) throws MessagingException {
        final String senderEmail = "trankhanhbang.010105@gmail.com";
        final String senderPassword = "uyqo qiay qjhn ndnm";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587"); // Dùng 587 thay vì 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Bật TLS
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");


        Session mailSession = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        Message message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);
        message.setText(content);

        Transport.send(message);
    }
}
