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
public class DeleteUserDelegate implements JavaDelegate {
    private static final Logger logger = Logger.getLogger(DeleteUserDelegate.class.getName());
    private final AdminService adminService;

    public DeleteUserDelegate(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            long deleteUserId = Long.parseLong((String) delegateExecution.getVariable("userId"));
            adminService.deletePerson(deleteUserId);
            logger.log(Level.INFO, "Current activity is " + delegateExecution.getCurrentActivityName());
            logger.log(Level.INFO, "User with id=" + deleteUserId + " is successfully deleted");
        } catch (Throwable throwable) {
            throw new BpmnError("delete_user_error", throwable.getMessage());
        }
    }
}
