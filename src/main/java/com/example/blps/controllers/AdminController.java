package com.example.blps.controllers;

import com.example.blps.exceptions.InvalidDataException;
import com.example.blps.exceptions.NoSuchTestException;
import com.example.blps.exceptions.NoSuchUserException;
import com.example.blps.model.dto.createTest.CreateTestDTO;
import com.example.blps.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/quiz")
    public ResponseEntity<?> createTest(@RequestBody CreateTestDTO createTestDTO) throws InvalidDataException {
        Map<Object, Object> model = new HashMap<>();
        model.put("id", adminService.createTest(createTestDTO));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @DeleteMapping("/quiz/{testId}")
    public ResponseEntity<?> deleteTest(@PathVariable Long testId) throws NoSuchTestException {
        Map<Object, Object> model = new HashMap<>();
        adminService.deleteTest(testId);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deletePerson(@PathVariable Long userId) throws NoSuchUserException {
        Map<Object, Object> model = new HashMap<>();
        adminService.deletePerson(userId);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
