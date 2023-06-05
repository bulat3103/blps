package com.example.blps.delegates;

import com.example.blps.services.EmailSenderService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Named
public class HandleEmailDelegate implements JavaDelegate {
    private static final Logger logger = Logger.getLogger(HandleEmailDelegate.class.getName());

    private final EmailSenderService emailSenderService;

    public HandleEmailDelegate(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            String emailSubject = (String) delegateExecution.getVariable("email-subject");
            String emailAddress = (String) delegateExecution.getVariable("email-address");
            String emailMessage = (String) delegateExecution.getVariable("email-message");
            emailSenderService.send(emailSubject, emailAddress, emailMessage);
            logger.log(Level.INFO, "Current activity is " + delegateExecution.getCurrentActivityName());
            logger.log(Level.INFO, "Email to " + emailAddress + " was successfully sent");
        } catch (Throwable throwable) {
            throw new BpmnError("handle-email-error", throwable.getMessage());
        }
    }
}
