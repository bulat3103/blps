package com.example.blps.services;

import com.example.blps.exceptions.InvalidDataException;
import com.example.blps.exceptions.NoSuchTestException;
import com.example.blps.model.*;
import com.example.blps.model.dto.*;
import com.example.blps.repositories.*;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private final TestRepository testRepository;
    private final TestQuestionRepository testQuestionRepository;
    private final QuestionRepository questionRepository;
    private final CommentRepository commentRepository;
    private final AnswerRepository answerRepository;
    private final TestResultRepository testResultRepository;

    public QuizService(
            TestRepository testRepository,
            TestQuestionRepository testQuestionRepository,
            QuestionRepository questionRepository,
            CommentRepository commentRepository,
            AnswerRepository answerRepository,
            TestResultRepository testResultRepository)
    {
        this.testRepository = testRepository;
        this.testQuestionRepository = testQuestionRepository;
        this.questionRepository = questionRepository;
        this.commentRepository = commentRepository;
        this.answerRepository = answerRepository;
        this.testResultRepository = testResultRepository;
    }

    public List<TestCommentsDTO> getAllTestComments(Long testId) throws NoSuchTestException {
        Optional<Test> oTest = testRepository.findById(testId);
        if (oTest.isEmpty()) {
            throw new NoSuchTestException("Теста с таким id не существует");
        }
        List<Comment> testsComments = commentRepository.getAllByTestId(testId);
        return testsComments.stream().map(Comment::toDTO).collect(Collectors.toList());
    }

    public void rateTest(Long testId, Integer rate) throws NoSuchTestException, InvalidDataException {
        Optional<Test> oTest = testRepository.findById(testId);
        if (oTest.isEmpty()) {
            throw new NoSuchTestException("Теста с таким id не существует");
        }
        if (rate < 0 || rate > 5) throw new InvalidDataException("Оценка должна быть в интервале от [0;5]");
        Test test = oTest.get();
        test.setPointsSum(test.getPointsSum() + rate);
        test.setPointsCount(test.getPointsCount() + 1);
        test.setRating(test.getPointsCount() == 0 ? 0 : test.getPointsSum() * 1.0 / test.getPointsCount());
        testRepository.save(test);
    }

    public QuestionDTO getQuestion(Long testId, Integer qNumber) throws NoSuchTestException, InvalidDataException {
        Optional<Test> oTest = testRepository.findById(testId);
        if (oTest.isEmpty()) {
            throw new NoSuchTestException("Теста с таким id не существует");
        }
        Integer count = testQuestionRepository.countByTestId(testId);
        if (qNumber <= 0 || qNumber > count) throw new InvalidDataException("Вопроса под таким номером не существует");
        Long qId = testQuestionRepository.getByTestIdAndNumber(testId, qNumber);
        List<String> answers = answerRepository.getAnswersByQuestionId(qId);
        Optional<Question> question = questionRepository.findById(qId);
        if (question.isEmpty()) throw new InvalidDataException("Такого вопроса не существует");
        return Question.toDto(question.get(), answers);
    }

    public Long writeComment(Long testId, WriteCommentDTO writeCommentDTO) throws NoSuchTestException {
        Optional<Test> oTest = testRepository.findById(testId);
        if (oTest.isEmpty()) {
            throw new NoSuchTestException("Теста с таким id не существует");
        }
        Comment comment = commentRepository.save(new Comment(oTest.get(), new Timestamp(System.currentTimeMillis()),
                writeCommentDTO.getWriter(), writeCommentDTO.getComment()));
        return comment.getId();
    }

    public String submitTest(TestAnswersDTO testAnswersDTO) throws NoSuchTestException, InvalidDataException {
        Optional<Test> oTest = testRepository.findById(testAnswersDTO.getTestId());
        if (oTest.isEmpty()) {
            throw new NoSuchTestException("Теста с таким id не существует");
        }
        Integer count = testQuestionRepository.countByTestId(testAnswersDTO.getTestId());
        Map<Integer, Integer> answers = testAnswersDTO.getAnswers();
        int testRate = 0;
        for (Map.Entry<Integer, Integer> entry : answers.entrySet()) {
            if (entry.getKey() > count || entry.getKey() <= 0) {
                throw new InvalidDataException("Вопроса под таким номером не существует");
            }
            Long qId = testQuestionRepository.getByTestIdAndNumber(testAnswersDTO.getTestId(), entry.getKey());
            testRate += answerRepository.getRateByQuestionAndAnsNum(qId, entry.getValue());
        }
        return testResultRepository.getDescByTestAndBounds(testAnswersDTO.getTestId(), testRate);
    }

    public Integer getTestQuestionsCount(Long testId) throws NoSuchTestException {
        Optional<Test> oTest = testRepository.findById(testId);
        if (oTest.isEmpty()) {
            throw new NoSuchTestException("Теста с таким id не существует");
        }
        return testQuestionRepository.countByTestId(testId);
    }

    public List<TestDTO> getAllTests(Integer limit, Integer offset, String sortType) throws InvalidDataException {
        if (limit < 0) throw new InvalidDataException("Limit должен быть положительным числом");
        if (offset < 0) throw new InvalidDataException("Offset должен быть положительным числом");
        return sortType.equals("ASC")
                ? testRepository.getAllTests(limit, offset).stream().map(Test::toDto).toList()
                : testRepository.getAllTestsBySortDesc(limit, offset).stream().map(Test::toDto).toList();
    }
}
