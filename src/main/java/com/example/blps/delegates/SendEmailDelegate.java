package com.example.blps.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.logging.Logger;

@Component
@Named
public class SendEmailDelegate implements JavaDelegate {
    private static final Logger logger = Logger.getLogger(SendEmailDelegate.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

    }
}
