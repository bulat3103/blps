package com.example.mailservice.controllers;

import com.example.mailservice.exeptions.InvalidDataException;
import com.example.mailservice.model.Mail;
import com.example.mailservice.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody Mail mail) throws InvalidDataException {
        Map<Object, Object> model = new HashMap<>();
        mailService.sendMail(mail);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
