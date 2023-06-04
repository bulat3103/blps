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
public class RateTestDelegate implements JavaDelegate {
    private static final Logger logger = Logger.getLogger(RateTestDelegate.class.getName());

    private QuizService quizService;
    private JwtUtil jwtUtil;

    public RateTestDelegate(QuizService quizService, JwtUtil jwtUtil) {
        this.quizService = quizService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            long testId = (long) delegateExecution.getVariable("testId");
            String rate = (String) delegateExecution.getVariable("rate");
            int rateInt = Integer.parseInt(rate);
            String username = jwtUtil.usernameFromToken((String) delegateExecution.getVariable("token"));
            quizService.rateTest(testId, rateInt, username);
            logger.log(Level.INFO, "Current activity is " + delegateExecution.getCurrentActivityName());
            logger.log(Level.INFO, "Test with id=" + testId + " was successfully rated");
        } catch (Throwable throwable) {
            throw new BpmnError("rate-test-error", throwable.getMessage());
        }
    }
}
