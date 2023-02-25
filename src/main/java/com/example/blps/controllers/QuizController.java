package com.example.blps.controllers;

import com.example.blps.exceptions.InvalidDataException;
import com.example.blps.exceptions.NoSuchTestException;
import com.example.blps.model.dto.QuestionDTO;
import com.example.blps.model.dto.TestAnswersDTO;
import com.example.blps.model.dto.WriteCommentDTO;
import com.example.blps.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping(value = "{testId}/question")
    public ResponseEntity<?> getQuestion(@PathVariable Long testId, @RequestParam("q") Integer qNumber) {
        Map<Object, Object> model = new HashMap<>();
        try {
            QuestionDTO question = quizService.getQuestion(testId, qNumber);
            model.put("question", question);
        } catch (NoSuchTestException | InvalidDataException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping(value = "{testId}/comments")
    public ResponseEntity<?> writeComment(@PathVariable Long testId,@RequestBody WriteCommentDTO writeCommentDTO) {
        Map<Object, Object> model = new HashMap<>();
        try {
            Long id = quizService.writeComment(testId, writeCommentDTO);
            model.put("id", id);
        } catch (NoSuchTestException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> submitTest(@RequestBody TestAnswersDTO testAnswersDTO) {
        Map<Object, Object> model = new HashMap<>();
        try {
            String result = quizService.submitTest(testAnswersDTO);
            model.put("result", result);
        } catch (NoSuchTestException | InvalidDataException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getListOfTests(@RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset) {
        Map<Object, Object> model = new HashMap<>();
        try {
            model.put("tests", quizService.getAllTests(limit, offset));
        } catch (InvalidDataException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping(value = "{testId}")
    public ResponseEntity<?> getQuestionsCount(@PathVariable Long testId) {
        Map<Object, Object> model = new HashMap<>();
        try {
            int count = quizService.getTestQuestionsCount(testId);
            model.put("count", count);
        } catch (NoSuchTestException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping("{testId}/rate")
    public ResponseEntity<?> rateTest(@PathVariable Long testId, @RequestParam("rate") String rate) {
        Map<Object, Object> model = new HashMap<>();
        try {
            Integer rateInt = Integer.parseInt(rate);
            quizService.rateTest(testId, rateInt);
        } catch (NoSuchTestException | InvalidDataException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException e) {
            model.put("message", "Рейтинг должен быть числом [0;5]");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping(value = "{testId}/comments")
    public ResponseEntity<?> getTestComments(@PathVariable Long testId) {
        Map<Object, Object> model = new HashMap<>();
        try {
            model.put("comments", quizService.getAllTestComments(testId));
        } catch (NoSuchTestException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
