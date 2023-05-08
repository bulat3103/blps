package com.example.app.model.dto.createTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateQuestionDTO {
    private String text;
    private List<CreateAnswerDTO> answers;
}
