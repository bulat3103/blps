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
            delegateExecution
                    .getProcessEngineServices()
                    .getRuntimeService()
                    .createMessageCorrelation("mail-send-request")
                    .setVariable("email-subject", delegateExecution.getVariable("email-subject"))
                    .setVariable("email-address", delegateExecution.getVariable("email-address"))
                    .setVariable("email-message", delegateExecution.getVariable("email-message"))
                    .correlate();
            logger.log(Level.INFO, "Current activity is " + delegateExecution.getCurrentActivityName());
        } catch (Throwable throwable) {
            throw new BpmnError("send-email-error", throwable.getMessage());
        }
    }
}
