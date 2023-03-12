package com.example.blps.model.dto.createTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTestResultDTO {
    private int leftBound;
    private int rightBound;
    private String description;
}
