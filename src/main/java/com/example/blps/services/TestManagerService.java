package com.example.blps.services;

import com.example.blps.exceptions.NoRightsException;
import com.example.blps.exceptions.NoSuchTestException;
import com.example.blps.model.*;
import com.example.blps.model.dto.TestStatusDTO;
import com.example.blps.model.dto.createTest.CreateTestDTO;
import com.example.blps.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestManagerService {
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

    public String createTest(CreateTestDTO createTestDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        try {
            messageSenderService.sendMessageToBroker(createTestDTO);
            testStatusRepository.save(new TestCreateStatus(user, "IN_PROGRESS", createTestDTO.getName()));
        } catch (Exception e) {
            return "Произошла ошибка при отправке данных";
        }
        return "Ваш запрос отправлен на модерацию";
    }

    @Transactional
    public void deleteTest(Long testId) throws NoSuchTestException, NoRightsException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        Optional<Test> testO = testRepository.findById(testId);
        if (!testO.isPresent()) throw new NoSuchTestException("Теста с таким id не существует");
        Test test = testO.get();
        if (!test.getOwner().getId().equals(user.getId())) {
            throw new NoRightsException("Этот тест принадлежит другому пользователю");
        }
        List<TestResult> testResultList = testResultRepository.findAllByTestId(test);
        List<Comment> commentList = commentRepository.getAllByTestId(test.getId());
        List<TestQuestion> testQuestionList = testQuestionRepository.getAllByTestId(test.getId());
        testResultRepository.deleteAll(testResultList);
        commentRepository.deleteAll(commentList);
        testQuestionRepository.deleteAll(testQuestionList);
        testRepository.delete(test);
    }

    public List<TestStatusDTO> getStatuses() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        List<TestCreateStatus> allByUser = testStatusRepository.getAllByUser(user.getId());
        return allByUser.stream().map(TestStatusDTO::toDto).collect(Collectors.toList());
    }
}
