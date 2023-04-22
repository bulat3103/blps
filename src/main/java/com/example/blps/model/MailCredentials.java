package com.example.blps.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailCredentials {
    private String email;
    private String newStatus;
    private String message;
}
