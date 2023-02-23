package com.example.blps.model;

import com.example.blps.model.dto.QuestionDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "questions")
public class Question {
    @Id
    private Long id;

    @Column(name = "text")
    private String text;

    public static QuestionDTO toDto(Question question, List<String> answers) {
        return new QuestionDTO(question.text, answers);
    }
}
