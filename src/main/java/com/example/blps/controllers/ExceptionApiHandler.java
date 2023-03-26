package com.example.blps.controllers;

import com.example.blps.exceptions.AuthorizeException;
import com.example.blps.exceptions.InvalidDataException;
import com.example.blps.exceptions.NoSuchTestException;
import com.example.blps.exceptions.NoSuchUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.security.auth.login.LoginException;

@RestControllerAdvice
public class ExceptionApiHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchTestException.class)
    protected ResponseEntity<Object> NoSuchTest(NoSuchTestException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(LoginException.class)
    protected ResponseEntity<Object> AuthException(LoginException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Проверьте правильность ввода логина и пароля");
    }

    @ExceptionHandler(NoSuchUserException.class)
    protected ResponseEntity<Object> NoSuchUser(NoSuchUserException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> mismatchException(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(InvalidDataException.class)
    protected ResponseEntity<Object> InvalidData(InvalidDataException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(NumberFormatException.class)
    protected ResponseEntity<Object> NumberFormat(NumberFormatException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Рейтинг должен быть числом [0;5]");
    }

    @ExceptionHandler(AuthorizeException.class)
    protected ResponseEntity<Object> Authorize(AuthorizeException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }
}