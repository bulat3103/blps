package com.example.blps.shedulers;

import com.example.blps.model.Rate;
import com.example.blps.model.Test;
import com.example.blps.repositories.RateRepository;
import com.example.blps.repositories.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RatesCleanerScheduler {
    private final TestRepository testRepository;
    private final RateRepository rateRepository;

    public RatesCleanerScheduler(TestRepository testRepository, RateRepository rateRepository) {
        this.testRepository = testRepository;
        this.rateRepository = rateRepository;
    }

    @Transactional
    public void cleanRateAndUpdateTestRate() {
        List<Rate> allRates = rateRepository.findAll();
        Map<Long, List<Rate>> groupRatesByTest = new HashMap<>();
        for (Rate rate : allRates) {
            if (!groupRatesByTest.containsKey(rate.getTestId().getId())) {
                List<Rate> newList = new ArrayList<>();
                newList.add(rate);
                groupRatesByTest.put(rate.getTestId().getId(), newList);
            } else {
                List<Rate> rates = groupRatesByTest.get(rate.getTestId().getId());
                rates.add(rate);
                groupRatesByTest.put(rate.getTestId().getId(), rates);
            }
        }
        List<Rate> rateToDelete = new ArrayList<>();
        for (Map.Entry<Long, List<Rate>> entry : groupRatesByTest.entrySet()) {
            List<Rate> rates = entry.getValue();
            rates.sort((o1, o2) -> (int) (o2.getDate().getTime() - o1.getDate().getTime()));
            int ratesSum = 0;
            for (int i = 0; i < Math.min(20, rates.size()); i++) {
                ratesSum += rates.get(i).getRate();
            }
            for (int i = 20; i < rates.size(); i++) {
                rateToDelete.add(rates.get(i));
            }
            Optional<Test> testO = testRepository.findById(entry.getKey());
            double newRate = Math.min(rates.size(), 20) == 0 ? 0 : 1.0 * ratesSum / Math.min(rates.size(), 20);
            if (testO.isPresent()) {
                Test updatedTest = testO.get();
                updatedTest.setRating(newRate);
                testRepository.save(updatedTest);
            }
        }
        rateRepository.deleteAll(rateToDelete);
    }
}
