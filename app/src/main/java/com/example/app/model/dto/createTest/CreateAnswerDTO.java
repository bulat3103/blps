package com.example.app.model.dto.createTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAnswerDTO {
    private String answer;
    private int rate;
}
