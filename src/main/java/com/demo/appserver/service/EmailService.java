package com.demo.appserver.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailContentBuilder emailContentBuilder;

    public void sendComplexEmail(String sendTo, String token) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            Map<String, String> contextMap = new HashMap<>();
            contextMap.put("email", sendTo);
            contextMap.put("token", token);
            String content = emailContentBuilder.build("reset-password",contextMap);
            messageHelper.setFrom("noreply@blahblah.com");
            messageHelper.setTo(sendTo);
            messageHelper.setSubject("ResetPassword");
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            throw e;
        }
    }

    
}