package com.example.blps.services;

import com.example.blps.exceptions.InvalidDataException;
import com.example.blps.model.*;
import com.example.blps.model.dto.TestStatusDTO;
import com.example.blps.model.dto.createTest.CreateAnswerDTO;
import com.example.blps.model.dto.createTest.CreateQuestionDTO;
import com.example.blps.model.dto.createTest.CreateTestDTO;
import com.example.blps.model.dto.createTest.CreateTestResultDTO;
import com.example.blps.model.enums.Status;
import com.example.blps.repositories.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestManagerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestStatusRepository testStatusRepository;

    public void createTest(String username, CreateTestDTO createTestDTO) throws JsonProcessingException, InvalidDataException {
        for (CreateQuestionDTO questionDTO : createTestDTO.getQuestions()) validateQuestions(questionDTO);
        for (CreateTestResultDTO resultDTO : createTestDTO.getResults()) validateTestResult(resultDTO);
        User user = userRepository.findUserByEmail(username);
        String json = objectMapper.writeValueAsString(createTestDTO);
        TestStatus testStatus = new TestStatus(user, Status.WAITING.name(), createTestDTO.getName(), json);
        testStatusRepository.save(testStatus);
    }

    public List<TestStatusDTO> getStatuses() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        List<TestStatus> allByUser = testStatusRepository.getAllByOwner(user.getId());
        return allByUser.stream().map(TestStatusDTO::toDto).collect(Collectors.toList());
    }

    private void validateQuestions(CreateQuestionDTO createQuestionDTO) throws InvalidDataException {
        if (createQuestionDTO.getText() == null || createQuestionDTO.getText().equals("")) {
            throw new InvalidDataException("Вопрос не может быть пустым");
        }
        for (CreateAnswerDTO answer : createQuestionDTO.getAnswers()) {
            if (answer.getRate() < 0) throw new InvalidDataException("Рейтинг ответа не может быть отрицательным");
            if (answer.getAnswer() == null || answer.getAnswer().equals("")) {
                throw new InvalidDataException("Ответ не может быть пустым");
            }
        }
    }

    private void validateTestResult(CreateTestResultDTO createTestResultDTO) throws InvalidDataException {
        if (createTestResultDTO.getLeftBound() < 0 || createTestResultDTO.getRightBound() < 0
                || createTestResultDTO.getRightBound() < createTestResultDTO.getLeftBound()) {
            throw new InvalidDataException("Границы результатов должно быть больше 0, левая граница должна быть меньше правой");
        }
        if (createTestResultDTO.getDescription() == null || createTestResultDTO.getDescription().equals("")) {
            throw new InvalidDataException("Описание результата не может быть пустым");
        }
    }
}
