package com.example.blps.services;

import com.example.blps.exceptions.InvalidDataException;
import com.example.blps.exceptions.NoSuchTestException;
import com.example.blps.exceptions.NoSuchUserException;
import com.example.blps.model.*;
import com.example.blps.model.dto.*;
import com.example.blps.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final UserRepository userRepository;

    public QuizService(
            TestRepository testRepository,
            TestQuestionRepository testQuestionRepository,
            QuestionRepository questionRepository,
            CommentRepository commentRepository,
            AnswerRepository answerRepository,
            TestResultRepository testResultRepository,
            UserRepository userRepository)
    {
        this.testRepository = testRepository;
        this.testQuestionRepository = testQuestionRepository;
        this.questionRepository = questionRepository;
        this.commentRepository = commentRepository;
        this.answerRepository = answerRepository;
        this.testResultRepository = testResultRepository;
        this.userRepository = userRepository;
    }

    public List<TestCommentsDTO> getAllTestComments(Long testId) throws NoSuchTestException {
        Optional<Test> oTest = testRepository.findById(testId);
        if (!oTest.isPresent()) {
            throw new NoSuchTestException("Теста с таким id не существует");
        }
        List<Comment> testsComments = commentRepository.getAllByTestId(testId);
        return testsComments.stream().map(Comment::toDTO).collect(Collectors.toList());
    }

    public void rateTest(Long testId, Integer rate) throws NoSuchTestException, InvalidDataException {
        Optional<Test> oTest = testRepository.findById(testId);
        if (!oTest.isPresent()) {
            throw new NoSuchTestException("Теста с таким id не существует");
        }
        if (rate < 0 || rate > 5) throw new InvalidDataException("Оценка должна быть в интервале от [0;5]");
        Test test = oTest.get();
        test.setPointsSum(test.getPointsSum() + rate);
        test.setPointsCount(test.getPointsCount() + 1);
        test.setRating(test.getPointsCount() == 0 ? 0 : test.getPointsSum() * 1.0 / test.getPointsCount());
        testRepository.save(test);
    }

    @Transactional
    public QuestionDTO getQuestion(Long testId, Integer qNumber) throws NoSuchTestException, InvalidDataException {
        Optional<Test> oTest = testRepository.findById(testId);
        if (!oTest.isPresent()) {
            throw new NoSuchTestException("Теста с таким id не существует");
        }
        Integer count = testQuestionRepository.countByTestId(testId);
        if (qNumber <= 0 || qNumber > count) throw new InvalidDataException("Вопроса под таким номером не существует");
        Long qId = testQuestionRepository.getByTestIdAndNumber(testId, qNumber);
        List<String> answers = answerRepository.getAnswersByQuestionId(qId);
        Optional<Question> question = questionRepository.findById(qId);
        if (!question.isPresent()) throw new InvalidDataException("Такого вопроса не существует");
        return Question.toDto(question.get(), answers);
    }

    public Long writeComment(Long testId, WriteCommentDTO writeCommentDTO) throws NoSuchTestException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        Optional<Test> oTest = testRepository.findById(testId);
        if (!oTest.isPresent()) {
            throw new NoSuchTestException("Теста с таким id не существует");
        }
        Comment comment = commentRepository.save(new Comment(oTest.get(), new Timestamp(System.currentTimeMillis()),
                user.getName(), writeCommentDTO.getComment()));
        return comment.getId();
    }

    @Transactional
    public String submitTest(TestAnswersDTO testAnswersDTO) throws NoSuchTestException, InvalidDataException {
        Optional<Test> oTest = testRepository.findById(testAnswersDTO.getTestId());
        if (!oTest.isPresent()) {
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
        if (!oTest.isPresent()) {
            throw new NoSuchTestException("Теста с таким id не существует");
        }
        return testQuestionRepository.countByTestId(testId);
    }

    public List<TestDTO> getAllTests(Integer page, Integer size, String sortType) throws InvalidDataException {
        if (page < 0) throw new InvalidDataException("Page должен быть положительным числом");
        if (size < 0) throw new InvalidDataException("Offset должен быть положительным числом");
        Pageable pagingSort = PageRequest.of(page, size,
                sortType.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, "rating");
        Page<Test> pageTests = testRepository.findAll(pagingSort);
        List<Test> tests = pageTests.getContent();
        return tests.stream().map(Test::toDto).collect(Collectors.toList());
    }
}
