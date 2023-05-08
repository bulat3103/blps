package com.example.mailservice.exeptions;

public class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}
