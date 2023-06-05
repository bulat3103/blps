package com.example.blps.delegates;

import com.example.blps.model.dto.createTest.CreateQuestionDTO;
import com.example.blps.model.dto.createTest.CreateTestDTO;
import com.example.blps.model.dto.createTest.CreateTestResultDTO;
import com.example.blps.security.JwtUtil;
import com.example.blps.services.TestManagerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Named
public class CreateTestDelegate implements JavaDelegate {
    private static final Logger logger = Logger.getLogger(CreateTestDelegate.class.getName());

    private final TestManagerService testManagerService;
    private final JwtUtil jwtUtil;

    public CreateTestDelegate(TestManagerService testManagerService, JwtUtil jwtUtil) {
        this.testManagerService = testManagerService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            String name = (String) delegateExecution.getVariable("name");
            String jsonQuestions = (String) delegateExecution.getVariable("questions");
            String jsonResults = (String) delegateExecution.getVariable("results");
            ObjectMapper mapper = new ObjectMapper();
            List<CreateQuestionDTO> createQuestionDTO = mapper.readValue(jsonQuestions, new TypeReference<>() {});
            List<CreateTestResultDTO> createTestResultDTO = mapper.readValue(jsonResults, new TypeReference<>() {});
            String username = jwtUtil.usernameFromToken((String) delegateExecution.getVariable("token"));
            testManagerService.createTest(username, new CreateTestDTO(
                    name,
                    createQuestionDTO,
                    createTestResultDTO
            ));
            delegateExecution.setVariable("result", "Test was successfully created");
            logger.log(Level.INFO, "Current activity is " + delegateExecution.getCurrentActivityName());
            logger.log(Level.INFO, "Test was successfully created");
        } catch (Throwable throwable) {
            throw new BpmnError("create-test-error", throwable.getMessage());
        }
    }
}
