package com.example.mailservice.services;

import com.example.mailservice.exeptions.InvalidDataException;
import com.example.mailservice.model.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class MailService {
    Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(Mail mail) throws InvalidDataException {
        if (checkCredentials(mail)) {
            logger.info("Preparing email");
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(mail.getEmailTo());
            simpleMailMessage.setSubject(mail.getSubject());
            simpleMailMessage.setText(mail.getMessage());
            mailSender.send(simpleMailMessage);
            logger.info("Email was sent");
        } else {
            throw new InvalidDataException("Невалидные данные");
        }
    }

    private boolean checkCredentials(Mail mail) {
        String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        boolean checkEmail = Pattern.compile(regexPattern).matcher(mail.getEmailTo()).matches();
        if (!checkEmail) {
            logger.error("Email address " + mail.getEmailTo() + " isn't valid");
        }
        boolean checkSubject = (mail.getSubject().equals("") || mail.getSubject() == null);
        if (checkSubject) {
            logger.error("Subject couldn't be empty");
        }
        boolean checkMessage = (mail.getMessage().equals("") || mail.getMessage() == null);
        if (checkMessage) {
            logger.error("Message couldn't be empty");
        }
        return checkEmail && !checkSubject && !checkMessage;
    }
}
