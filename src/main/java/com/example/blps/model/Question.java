package com.example.blps.model;

import com.example.blps.model.dto.QuestionDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "questions")
public class Question {
    @Id
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "answers")
    private String answers;

    public static QuestionDTO toDto(Question question) {
        return new QuestionDTO(question.text, question.answers);
    }
}
