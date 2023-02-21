package com.example.blps.model.dto;

import lombok.Data;

import java.util.Map;

@Data
public class TestAnswersDTO {
    private Long testId;
    private Map<Integer, Integer> answers;
}
