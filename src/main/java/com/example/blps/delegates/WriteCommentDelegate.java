package com.example.blps.delegates;

import com.example.blps.security.JwtUtil;
import com.example.blps.services.QuizService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Named
public class WriteCommentDelegate implements JavaDelegate {
    private static final Logger logger = Logger.getLogger(WriteCommentDelegate.class.getName());

    private QuizService quizService;
    private JwtUtil jwtUtil;

    public WriteCommentDelegate(QuizService quizService, JwtUtil jwtUtil) {
        this.quizService = quizService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            long testId = (long) delegateExecution.getVariable("testId");
            String comment = (String) delegateExecution.getVariable("comment");
            String username = jwtUtil.usernameFromToken((String) delegateExecution.getVariable("token"));
            Long id = quizService.writeComment(testId, comment, username);
            logger.log(Level.INFO, "Current activity is " + delegateExecution.getCurrentActivityName());
            logger.log(Level.INFO, "Comment with id=" + id + " for the test " + testId + " was successfully created");
        } catch (Throwable throwable) {
            throw new BpmnError("write-comment-error", throwable.getMessage());
        }
    }
}
