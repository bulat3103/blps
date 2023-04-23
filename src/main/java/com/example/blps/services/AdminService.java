package com.example.blps.services;

import com.example.blps.exceptions.InvalidDataException;
import com.example.blps.exceptions.NoSuchTestException;
import com.example.blps.exceptions.NoSuchUserException;
import com.example.blps.model.*;
import com.example.blps.model.dto.ChangeStatusDTO;
import com.example.blps.model.dto.TestStatusDTO;
import com.example.blps.model.dto.createTest.CreateAnswerDTO;
import com.example.blps.model.dto.createTest.CreateQuestionDTO;
import com.example.blps.model.dto.createTest.CreateTestDTO;
import com.example.blps.model.dto.createTest.CreateTestResultDTO;
import com.example.blps.model.enums.Status;
import com.example.blps.model.ids.AnswerId;
import com.example.blps.model.ids.TestQuestionId;
import com.example.blps.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestQuestionRepository testQuestionRepository;
    @Autowired
    private TestResultRepository testResultRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MessageSenderService messageSenderService;
    @Autowired
    private TestStatusRepository testStatusRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    public void deletePerson(Long userId) throws NoSuchUserException {
        if (!userRepository.existsById(userId)) throw new NoSuchUserException("Пользователя с таким id не существует");
        userRepository.deleteById(userId);
    }

    @Transactional
    public void deleteTest(Long testId) throws NoSuchTestException {
        Optional<Test> testO = testRepository.findById(testId);
        if (!testO.isPresent()) throw new NoSuchTestException("Теста с таким id не существует");
        Test test = testO.get();
        List<TestResult> testResultList = testResultRepository.findAllByTestId(test);
        List<Comment> commentList = commentRepository.getAllByTestId(test.getId());
        List<TestQuestion> testQuestionList = testQuestionRepository.getAllByTestId(test.getId());
        testResultRepository.deleteAll(testResultList);
        commentRepository.deleteAll(commentList);
        testQuestionRepository.deleteAll(testQuestionList);
        testRepository.delete(test);
    }

    @Transactional
    public void changeTestStatus(Long statusId, ChangeStatusDTO changeStatusDTO) throws InvalidDataException, IOException {
        if (Arrays.stream(Status.values()).noneMatch(e -> e.name().equals(changeStatusDTO.getStatus().toLowerCase()))) {
            throw new InvalidDataException("Такого типа статуса не существует (WAITING, APPROVE, REJECT)");
        }
        Optional<TestStatus> testStatusOptional = testStatusRepository.findById(statusId);
        if (!testStatusOptional.isPresent()) throw new InvalidDataException("Такой записи не существует");
        TestStatus testStatus = testStatusOptional.get();
        CreateTestDTO createTestDTO = objectMapper.readValue(testStatus.getTestJson(), CreateTestDTO.class);
        if (changeStatusDTO.getStatus().equals("APPROVE")) {
            createTest(createTestDTO);
        }
        testStatus.setStatus(changeStatusDTO.getStatus());
        testStatusRepository.save(testStatus);
        MailCredentials mailCredentials = new MailCredentials(
                testStatus.getUserId().getEmail(),
                "Статус теста " + createTestDTO.getName() + " изменился на " + changeStatusDTO.getStatus(),
                changeStatusDTO.getMessage());
        try {
            messageSenderService.sendMessageToBroker(mailCredentials);
        } catch (Exception e) {
            System.out.println("Возникла ошибка отправки пакета брокеру");
        }
    }

    @Transactional
    public void createTest(CreateTestDTO createTestDTO) {
        Test newTest = testRepository.save(new Test(createTestDTO.getName(), 0));
        for (CreateTestResultDTO resultDTO : createTestDTO.getResults()) {
            testResultRepository.save(new TestResult(newTest, resultDTO.getLeftBound(), resultDTO.getRightBound(), resultDTO.getDescription()));
        }
        List<Question> qIds = new ArrayList<>();
        for (CreateQuestionDTO questionDTO : createTestDTO.getQuestions()) {
            Optional<Question> qO = questionRepository.findByText(questionDTO.getText());
            if (qO.isPresent()) {
                qIds.add(qO.get());
            } else {
                Question question = questionRepository.save(new Question(questionDTO.getText()));
                qIds.add(question);
                int num = 1;
                for (CreateAnswerDTO answerDTO : questionDTO.getAnswers()) {
                    answerRepository.save(new Answer(
                            new AnswerId(question, num++),
                            answerDTO.getAnswer(),
                            answerDTO.getRate()
                    ));
                }
            }
        }
        for (int i = 0; i < qIds.size(); i++) {
            testQuestionRepository.save(new TestQuestion(new TestQuestionId(newTest, qIds.get(i)), i + 1));
        }
    }

    public List<TestStatusDTO> getAllTestByStatus(String status) throws InvalidDataException {
        if (Arrays.stream(Status.values()).noneMatch(e -> e.name().equals(status.toLowerCase()))) {
            throw new InvalidDataException("Такого типа статуса не существует (WAITING, APPROVE, REJECT)");
        }
        return testStatusRepository.getAllByStatus(status).stream().map(TestStatusDTO::toDto).collect(Collectors.toList());
    }
}
