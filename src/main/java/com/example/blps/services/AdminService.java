package com.example.blps.services;

import com.example.blps.exceptions.InvalidDataException;
import com.example.blps.exceptions.NoSuchTestException;
import com.example.blps.exceptions.NoSuchUserException;
import com.example.blps.model.*;
import com.example.blps.model.dto.createTest.CreateAnswerDTO;
import com.example.blps.model.dto.createTest.CreateQuestionDTO;
import com.example.blps.model.dto.createTest.CreateTestDTO;
import com.example.blps.model.dto.createTest.CreateTestResultDTO;
import com.example.blps.model.ids.AnswerId;
import com.example.blps.model.ids.TestQuestionId;
import com.example.blps.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestQuestionRepository testQuestionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private CommentRepository commentRepository;

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
    public Long createTest(CreateTestDTO createTestDTO) throws InvalidDataException {
        for (CreateQuestionDTO questionDTO : createTestDTO.getQuestions()) validateQuestions(questionDTO);
        for (CreateTestResultDTO resultDTO : createTestDTO.getResults()) validateTestResult(resultDTO);
        Test newTest = testRepository.save(new Test(createTestDTO.getName()));
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
        return newTest.getId();
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
