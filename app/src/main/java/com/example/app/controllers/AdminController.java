package com.example.app.controllers;

import com.example.app.exceptions.InvalidDataException;
import com.example.app.exceptions.NoSuchTestException;
import com.example.app.exceptions.NoSuchUserException;
import com.example.app.model.dto.ChangeStatusDTO;
import com.example.app.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

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

    @PostMapping("/quiz/status/{statusId}")
    public ResponseEntity<?> changeStatus(@PathVariable Long statusId, @RequestBody ChangeStatusDTO changeStatusDTO) throws InvalidDataException, IOException {
        Map<Object, Object> model = new HashMap<>();
        adminService.changeTestStatus(statusId, changeStatusDTO);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping("/quiz/status")
    public ResponseEntity<?> quizStatuses(@RequestParam("status") String status) throws InvalidDataException {
        Map<Object, Object> model = new HashMap<>();
        model.put("tests", adminService.getAllTestByStatus(status));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
