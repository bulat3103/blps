package com.example.blps.services;

import com.example.blps.exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@Service
public class EmailSenderService {
    private static final Logger logger = Logger.getLogger(EmailSenderService.class.getName());

    @Autowired
    private JavaMailSenderImpl javaMailSenderImpl;

    public void send(String subject, String address, String message) throws InvalidDataException {
        if (!checkCredentials(subject, address, message)) throw new InvalidDataException("Невалидные данные");
        MimeMessage msg = javaMailSenderImpl.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(msg, true);
            helper.setTo(address);
            helper.setText(message);
            helper.setSubject(subject);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        javaMailSenderImpl.send(msg);
    }

    private boolean checkCredentials(String subject, String address, String message) {
        String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        boolean checkEmail = Pattern.compile(regexPattern).matcher(address).matches();
        if (!checkEmail) {
            logger.log(Level.WARNING, "Email address " + address + " isn't valid");
        }
        boolean checkSubject = subject.equals("");
        if (checkSubject) {
            logger.log(Level.WARNING, "Subject couldn't be empty");
        }
        boolean checkMessage = message.equals("");
        if (checkMessage) {
            logger.log(Level.WARNING, "Message couldn't be empty");
        }
        return checkEmail && !checkSubject && !checkMessage;
    }
}
