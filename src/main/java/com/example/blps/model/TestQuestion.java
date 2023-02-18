package com.example.blps.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "test_question")
@NoArgsConstructor
public class TestQuestion {
    @EmbeddedId
    private TestQuestionId id;

    @Column(name = "number")
    private Integer number;

    public TestQuestion(TestQuestionId id, Integer number) {
        this.id = id;
        this.number = number;
    }
}
