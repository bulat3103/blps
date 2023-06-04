package com.example.blps.delegates;

import com.example.blps.shedulers.RatesCleanerScheduler;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Named
public class RatesCleanerDelegate implements JavaDelegate {
    private static final Logger logger = Logger.getLogger(RatesCleanerDelegate.class.getName());

    private final RatesCleanerScheduler ratesCleanerScheduler;

    public RatesCleanerDelegate(RatesCleanerScheduler ratesCleanerScheduler) {
        this.ratesCleanerScheduler = ratesCleanerScheduler;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            ratesCleanerScheduler.cleanRateAndUpdateTestRate();
            logger.log(Level.INFO, "Rates were successfully deleted");
        } catch (Throwable throwable) {
            throw new BpmnError("rates-cleaner-error", throwable.getMessage());
        }
    }
}
