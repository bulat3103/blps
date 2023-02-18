package com.example.blps.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TestAnswersDTO {
    private Long testId;
    private Map<Integer, List<Integer>> answers;
}
