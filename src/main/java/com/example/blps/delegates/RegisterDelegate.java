package com.example.blps.delegates;

import com.example.blps.model.User;
import com.example.blps.model.dto.RegisterRequestDTO;
import com.example.blps.services.AuthorizationService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Named
public class RegisterDelegate implements JavaDelegate {
    private static final Logger logger = Logger.getLogger(RegisterDelegate.class.getName());
    private final AuthorizationService authorizationService;

    public RegisterDelegate(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            String name = (String) delegateExecution.getVariable("name");
            String username = (String) delegateExecution.getVariable("username");
            String password = (String) delegateExecution.getVariable("password");
            User user = authorizationService.registerUser(new RegisterRequestDTO(name, username, password));
            logger.log(Level.INFO, "Current activity is " + delegateExecution.getCurrentActivityName());
            logger.log(Level.INFO, "New user signed up with username=" + user.getEmail() + " and id=" + user.getId());
        } catch (Throwable throwable) {
            throw new BpmnError("register_error", throwable.getMessage());
        }
    }
}
