package com.example.mailservice.controllers;

import com.example.mailservice.exeptions.InvalidDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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

    @ExceptionHandler(InvalidDataException.class)
    protected ResponseEntity<Object> InvalidData(InvalidDataException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(doModel(HttpStatus.BAD_REQUEST, exception.getMessage(), "InvalidDataException"));
    }
}