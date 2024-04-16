package com.monapp.monapp.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String from,String to,  String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        mimeMessage.setContent(body, "text/html");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        System.out.println("message envoyeeeeee");
        javaMailSender.send(mimeMessage);
    }

}