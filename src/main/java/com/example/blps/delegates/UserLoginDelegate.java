package com.example.blps.delegates;

import com.example.blps.exceptions.AuthorizeException;
import com.example.blps.model.User;
import com.example.blps.model.dto.LoginRequestDTO;
import com.example.blps.model.enums.RoleName;
import com.example.blps.security.JwtUtil;
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
public class UserLoginDelegate implements JavaDelegate {
    private static final Logger logger = Logger.getLogger(UserLoginDelegate.class.getName());
    private final JwtUtil jwtUtil;
    private final AuthorizationService authorizationService;

    public UserLoginDelegate(JwtUtil jwtUtil, AuthorizationService authorizationService) {
        this.jwtUtil = jwtUtil;
        this.authorizationService = authorizationService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            User user = authorizationService.authUser(new LoginRequestDTO(
                    (String) delegateExecution.getVariable("username"),
                    (String) delegateExecution.getVariable("password")));
            String token = jwtUtil.generateToken(user.getEmail());
            delegateExecution.setVariable("token", token);
            if (!user.getRole().getName().equals(RoleName.USER)) throw new AuthorizeException("У вас нет прав");
            logger.log(Level.INFO, "Current activity is " + delegateExecution.getCurrentActivityName());
            logger.log(Level.INFO, "User with email " + user.getEmail() + " is successfully signed in");
        } catch (Throwable throwable) {
            throw new BpmnError("login_error", throwable.getMessage());
        }
    }
}
