package com.example.blps.delegates;

import com.example.blps.model.dto.TestAnswersDTO;
import com.example.blps.services.QuizService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Named
public class SubmitTestDelegate implements JavaDelegate {
    private static final Logger logger = Logger.getLogger(SubmitTestDelegate.class.getName());

    private final QuizService quizService;

    public SubmitTestDelegate(QuizService quizService) {
        this.quizService = quizService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            long testId = Long.parseLong((String) delegateExecution.getVariable("testId"));
            String jsonAnswers = (String) delegateExecution.getVariable("answers");
            ObjectMapper mapper = new ObjectMapper();
            TestAnswersDTO testAnswersDTO = mapper.readValue(jsonAnswers, TestAnswersDTO.class);
            String result = quizService.submitTest(testId, testAnswersDTO);
            delegateExecution.setVariable("result", result);
            logger.log(Level.INFO, "Current activity is " + delegateExecution.getCurrentActivityName());
            logger.log(Level.INFO, "Result for the test with id = " + testId + " is " + result);
        } catch (Throwable throwable) {
            throw new BpmnError("submit-test-error", throwable.getMessage());
        }
    }
}
