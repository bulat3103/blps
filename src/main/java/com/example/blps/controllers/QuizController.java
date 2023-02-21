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

    @GetMapping(value = "question")
    public ResponseEntity<?> getQuestion(@RequestParam("test") Long testId, @RequestParam("q") Integer qNumber) {
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

    @PostMapping(value = "comment")
    public ResponseEntity<?> writeComment(@RequestBody WriteCommentDTO writeCommentDTO) {
        Map<Object, Object> model = new HashMap<>();
        try {
            Long id = quizService.writeComment(writeCommentDTO);
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
    public ResponseEntity<?> getQuestionsCount(@RequestParam("test") Long testId) {
        Map<Object, Object> model = new HashMap<>();
        try {
            quizService.getTestQuestionsCount(testId);
        } catch (NoSuchTestException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping("rate")
    public ResponseEntity<?> rateTest(@RequestParam("test") Long testId, @RequestParam("rate") Integer rate) {
        Map<Object, Object> model = new HashMap<>();
        try {
            quizService.rateTest(testId, rate);
        } catch (NoSuchTestException | InvalidDataException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping(value = "comment")
    public ResponseEntity<?> getTestComments(@RequestParam("test") Long testId) {
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
