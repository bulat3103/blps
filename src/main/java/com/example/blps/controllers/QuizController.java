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
    public ResponseEntity<?> getQuestion(@PathVariable Long testId, @RequestParam("q") Integer qNumber)
            throws NoSuchTestException, InvalidDataException {
        Map<Object, Object> model = new HashMap<>();
        QuestionDTO question = quizService.getQuestion(testId, qNumber);
        model.put("question", question);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping(value = "{testId}/comments")
    public ResponseEntity<?> writeComment(@PathVariable Long testId,@RequestBody WriteCommentDTO writeCommentDTO)
    throws NoSuchTestException{
        Map<Object, Object> model = new HashMap<>();
        Long id = quizService.writeComment(testId, writeCommentDTO);
        model.put("id", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> submitTest(@RequestBody TestAnswersDTO testAnswersDTO)
            throws NoSuchTestException, InvalidDataException {
        Map<Object, Object> model = new HashMap<>();
        String result = quizService.submitTest(testAnswersDTO);
        model.put("result", result);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getListOfTests(@RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset, @RequestParam("sort") String type)
    throws  InvalidDataException{
        Map<Object, Object> model = new HashMap<>();
        if (!type.equals("ASC") && !type.equals("DESC")) throw new InvalidDataException("Сортировка имеет два типа: ASC, DESC");
        model.put("tests", quizService.getAllTests(limit, offset, type));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping(value = "{testId}")
    public ResponseEntity<?> getQuestionsCount(@PathVariable Long testId) throws NoSuchTestException  {
        Map<Object, Object> model = new HashMap<>();
        int count = quizService.getTestQuestionsCount(testId);
        model.put("count", count);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping("{testId}/rate")
    public ResponseEntity<?> rateTest(@PathVariable Long testId, @RequestParam("rate") String rate)
            throws NoSuchTestException, InvalidDataException, NumberFormatException {
        Map<Object, Object> model = new HashMap<>();
        Integer rateInt = Integer.parseInt(rate);
        quizService.rateTest(testId, rateInt);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping(value = "{testId}/comments")
    public ResponseEntity<?> getTestComments(@PathVariable Long testId) throws NoSuchTestException {
        Map<Object, Object> model = new HashMap<>();
        model.put("comments", quizService.getAllTestComments(testId));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

}
