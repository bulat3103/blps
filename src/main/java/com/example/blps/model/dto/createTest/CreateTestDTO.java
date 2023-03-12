package com.example.blps.model.dto.createTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTestDTO {
    private String name;
    private List<CreateQuestionDTO> questions;
    private List<CreateTestResultDTO> results;
}
