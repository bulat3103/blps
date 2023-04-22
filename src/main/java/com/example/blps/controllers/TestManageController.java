package com.example.blps.controllers;

import com.example.blps.exceptions.InvalidDataException;
import com.example.blps.exceptions.NoRightsException;
import com.example.blps.exceptions.NoSuchTestException;
import com.example.blps.model.dto.createTest.CreateTestDTO;
import com.example.blps.services.TestManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("manage")
public class TestManageController {

    private final TestManagerService testManagerService;

    public TestManageController(TestManagerService testManagerService) {
        this.testManagerService = testManagerService;
    }

    @PostMapping("/quiz")
    public ResponseEntity<?> createTest(@RequestBody CreateTestDTO createTestDTO) {
        Map<Object, Object> model = new HashMap<>();
        model.put("id", testManagerService.createTest(createTestDTO));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @DeleteMapping("/quiz/{testId}")
    public ResponseEntity<?> deleteTest(@PathVariable Long testId) throws NoSuchTestException, NoRightsException {
        Map<Object, Object> model = new HashMap<>();
        testManagerService.deleteTest(testId);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping("/statuses")
    public ResponseEntity<?> getStatuses() {
        Map<Object, Object> model = new HashMap<>();
        model.put("statuses", testManagerService.getStatuses());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
