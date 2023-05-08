package com.example.app.controllers;

import com.example.app.exceptions.InvalidDataException;
import com.example.app.model.dto.createTest.CreateTestDTO;
import com.example.app.services.TestManagerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/manage")
public class TestManageController {

    private final TestManagerService testManagerService;

    public TestManageController(TestManagerService testManagerService) {
        this.testManagerService = testManagerService;
    }

    @PostMapping("/quiz")
    public ResponseEntity<?> createTest(@RequestBody CreateTestDTO createTestDTO) throws InvalidDataException, JsonProcessingException {
        Map<Object, Object> model = new HashMap<>();
        testManagerService.createTest(createTestDTO);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping("/statuses")
    public ResponseEntity<?> getStatuses() {
        Map<Object, Object> model = new HashMap<>();
        model.put("statuses", testManagerService.getStatuses());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
