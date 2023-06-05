package com.example.blps.delegates;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Named
public class SendEmailDelegate implements JavaDelegate {
    private static final Logger logger = Logger.getLogger(SendEmailDelegate.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            logger.log(Level.INFO, "Message was sent to another process");
            logger.log(Level.INFO, "Current activity is " + delegateExecution.getCurrentActivityName());
            delegateExecution
                    .getProcessEngineServices()
                    .getRuntimeService()
                    .createMessageCorrelation("mail-send-request")
                    .setVariable("email-subject", delegateExecution.getVariable("email-subject"))
                    .setVariable("email-address", delegateExecution.getVariable("email-address"))
                    .setVariable("email-message", delegateExecution.getVariable("email-message"))
                    .correlate();
        } catch (Throwable throwable) {
            throw new BpmnError("send-email-error", throwable.getMessage());
        }
    }
}
