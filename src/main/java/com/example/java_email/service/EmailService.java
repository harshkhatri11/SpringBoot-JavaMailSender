package com.example.java_email.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String sendSimpleMail(String from, String to) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject("This is Simple Text Based Mail");
            message.setText("Hi Hope you are doing well!!");
            mailSender.send(message);
            return "Mail sent successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String sendMailWithAttachment(String from, String to) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("Your Requested Documents from Spring Boot Application");
            helper.setText(
                    "Hello,\n\n" +
                            "Please find the attached documents.\n\n" +
                            "If you have any questions or need further assistance, feel free to reach out.\n\n",
                    false // false = plain text
            );
            helper.addAttachment("SpringBoot.png", new ClassPathResource("static/SpringBoot.png"));
            helper.addAttachment("Terms & Conditions Template.pdf", new ClassPathResource("static/Terms & Conditions Template.pdf"));
            mailSender.send(message);
            return "Mail sent successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String sendMailWithHTMLContent(String from, String to) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("Spring Boot Mail Delivery 📬");

            // Load HTML file from classpath
            ClassPathResource htmlResource = new ClassPathResource("templates/email-spring-template.html");
            String htmlContent = new String(htmlResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            helper.setText(htmlContent, true);

            // Inline image
            helper.addInline("logoImage", new ClassPathResource("static/SpringBoot.png"));

            // Attach files
            helper.addAttachment("SpringBoot.png", new ClassPathResource("static/SpringBoot.png"));
            helper.addAttachment("Terms & Conditions Template.pdf", new ClassPathResource("static/Terms & Conditions Template.pdf"));

            mailSender.send(message);
            return "Styled HTML email sent via Spring Boot!";
        } catch (Exception e) {
            return "Error sending styled mail: " + e.getMessage();
        }
    }
}
