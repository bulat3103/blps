package com.example.blps.controllers;

import com.example.blps.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.security.auth.login.LoginException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionApiHandler extends ResponseEntityExceptionHandler {

    private static Map<Object, Object> doModel(HttpStatus status, String message, String error) {
        Map<Object, Object> model = new HashMap<>();
        model.put("timestamp", Instant.now());
        model.put("status", status);
        model.put("message", message);
        model.put("error", error);
        return model;
    }

    @ExceptionHandler(NoSuchTestException.class)
    protected ResponseEntity<Object> NoSuchTest(NoSuchTestException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(doModel(HttpStatus.NOT_FOUND, exception.getMessage(), "NoSuchTestException"));
    }

    @ExceptionHandler(LoginException.class)
    protected ResponseEntity<Object> AuthException(LoginException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(doModel(
                        HttpStatus.BAD_REQUEST,
                        "Проверьте правильность ввода логина и пароля",
                        "LoginException"));
    }

    @ExceptionHandler(NoSuchUserException.class)
    protected ResponseEntity<Object> NoSuchUser(NoSuchUserException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(doModel(HttpStatus.NOT_FOUND, exception.getMessage(), "NoSuchUserException"));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> mismatchException(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(doModel(HttpStatus.NOT_FOUND, exception.getMessage(), "MethodArgumentTypeMismatchException"));
    }

    @ExceptionHandler(InvalidDataException.class)
    protected ResponseEntity<Object> InvalidData(InvalidDataException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(doModel(HttpStatus.BAD_REQUEST, exception.getMessage(), "InvalidDataException"));
    }

    @ExceptionHandler(AuthorizeException.class)
    protected ResponseEntity<Object> Authorize(AuthorizeException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(doModel(HttpStatus.FORBIDDEN, exception.getMessage(), "AuthorizeException"));
    }

    @ExceptionHandler(NoRightsException.class)
    protected ResponseEntity<Object> NoRights(NoRightsException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(doModel(HttpStatus.FORBIDDEN, exception.getMessage(), "NoRightsException"));
    }
}