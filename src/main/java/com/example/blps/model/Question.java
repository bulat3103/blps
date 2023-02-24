package com.example.blps.model;

import com.example.blps.model.dto.QuestionDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "questions")
public class Question {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    public static QuestionDTO toDto(Question question, List<String> answers) {
        return new QuestionDTO(question.text, answers);
    }
}
