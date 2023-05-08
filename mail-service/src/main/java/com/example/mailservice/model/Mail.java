package com.example.mailservice.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Mail implements Serializable {
    private final String subject;
    private final String emailTo;
    private final String message;
}
