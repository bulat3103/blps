package com.example.blps.services;

import com.example.blps.exceptions.InvalidDataException;
import com.example.blps.exceptions.NoSuchTestException;
import com.example.blps.model.*;
import com.example.blps.repositories.CommentRepository;
import com.example.blps.repositories.QuestionRepository;
import com.example.blps.repositories.TestQuestionRepository;
import com.example.blps.repositories.TestRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private final TestRepository testRepository;
    private final TestQuestionRepository testQuestionRepository;
    private final QuestionRepository questionRepository;
    private final CommentRepository commentRepository;

    public QuizService(TestRepository testRepository, TestQuestionRepository testQuestionRepository, QuestionRepository questionRepository, CommentRepository commentRepository) {
        this.testRepository = testRepository;
        this.testQuestionRepository = testQuestionRepository;
        this.questionRepository = questionRepository;
        this.commentRepository = commentRepository;
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
        return Question.toDto(questionRepository.findById(qId).get());
    }

    public Long writeComment(WriteCommentDTO writeCommentDTO) throws NoSuchTestException {
        Optional<Test> oTest = testRepository.findById(writeCommentDTO.getTestId());
        if (oTest.isEmpty()) {
            throw new NoSuchTestException("Теста с таким id не существует");
        }
        Comment comment = commentRepository.save(new Comment(oTest.get(), new Timestamp(System.currentTimeMillis()),
                writeCommentDTO.getWriter(), writeCommentDTO.getComment()));
        return comment.getId();
    }

    public Map<Integer, Integer> submitTest() {
        return null;
    }
}
