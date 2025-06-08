package com.example.java_email.controller;

import com.example.java_email.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @Value("${app.mail.from}")
    private String from;

    @Value("${app.mail.to}")
    private String to;

    @GetMapping("/send-email")
    public String sendEmail() {
        return emailService.sendSimpleMail(from, to);
    }

    @GetMapping("/send-email-with-attachment")
    public String sendEmailWithAttachment() {
        return emailService.sendMailWithAttachment(from, to);
    }

    @GetMapping("/send-html-email")
    public String sendMailWithHTMLContent() {
        return emailService.sendMailWithHTMLContent(from, to);
    }
}
