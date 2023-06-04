package com.example.blps.delegates;

import com.example.blps.model.MailCredentials;
import com.example.blps.model.dto.ChangeStatusDTO;
import com.example.blps.security.JwtUtil;
import com.example.blps.services.AdminService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Named
public class ChangeStatusDelegate implements JavaDelegate {
    private static final Logger logger = Logger.getLogger(ChangeStatusDelegate.class.getName());

    private final AdminService adminService;

    public ChangeStatusDelegate(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            long statusId = (long) delegateExecution.getVariable("statusId");
            String status = (String) delegateExecution.getVariable("status");
            String message = (String) delegateExecution.getVariable("statusMessage");
            MailCredentials mailCredentials = adminService.changeTestStatus(statusId, new ChangeStatusDTO(status, message));
            delegateExecution.setVariable("email-subject", mailCredentials.getSubject());
            delegateExecution.setVariable("email-address", mailCredentials.getEmailTo());
            delegateExecution.setVariable("email-message", mailCredentials.getMessage());
            logger.log(Level.INFO, "Current activity is " + delegateExecution.getCurrentActivityName());
            logger.log(Level.INFO, "Status with id = " + statusId + " was successfully changed on " + status);
        } catch (Throwable throwable) {
            throw new BpmnError("change-status-error", throwable.getMessage());
        }
    }
}
