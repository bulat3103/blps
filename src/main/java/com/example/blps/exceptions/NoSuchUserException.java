package com.example.blps.exceptions;

public class NoSuchUserException extends Exception{
    public NoSuchUserException(String message) {
        super(message);
    }
}