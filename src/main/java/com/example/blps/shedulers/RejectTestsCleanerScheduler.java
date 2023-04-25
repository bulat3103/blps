package com.example.blps.shedulers;

import com.example.blps.model.TestStatus;
import com.example.blps.repositories.TestStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EnableScheduling
@Service
public class RejectTestsCleanerScheduler {
    @Autowired
    private TestStatusRepository testStatusRepository;

    @Scheduled(fixedRate = 120_000)
    @Transactional
    public void cleanRejectStatuses() {
        List<TestStatus> reject = testStatusRepository.getAllByStatus("REJECT");
        testStatusRepository.deleteAll(reject);
    }
}
