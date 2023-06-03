package com.example.blps.delegates;

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
public class DeleteTestDelegate implements JavaDelegate {
    private static final Logger logger = Logger.getLogger(DeleteTestDelegate.class.getName());

    private final AdminService adminService;

    public DeleteTestDelegate(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            long testId = (long) delegateExecution.getVariable("testId");
            adminService.deleteTest(testId);
            logger.log(Level.INFO, "Test with id=" + testId + " is successfully deleted");
        } catch (Throwable throwable) {
            throw new BpmnError("delete_test_error", throwable.getMessage());
        }
    }
}
