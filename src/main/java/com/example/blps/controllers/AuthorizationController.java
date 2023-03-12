package com.example.blps.controllers;

import com.example.blps.exceptions.InvalidDataException;
import com.example.blps.exceptions.NoSuchUserException;
import com.example.blps.model.User;
import com.example.blps.model.dto.LoginRequestDTO;
import com.example.blps.model.dto.RegisterRequestDTO;
import com.example.blps.security.JwtUtil;
import com.example.blps.services.AuthorizationService;
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
@RequestMapping("auth")
public class AuthorizationController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<?> authUser(@RequestBody LoginRequestDTO loginRequestDTO) throws InvalidDataException, NoSuchUserException {
        Map<Object, Object> model = new HashMap<>();
        User authUser = authorizationService.authUser(loginRequestDTO);
        model.put("token", jwtUtil.generateToken(authUser.getEmail()));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDTO registerRequestDTO) throws InvalidDataException {
        Map<Object, Object> model = new HashMap<>();
        User newUser = authorizationService.registerUser(registerRequestDTO);
        model.put("token", jwtUtil.generateToken(newUser.getEmail()));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
