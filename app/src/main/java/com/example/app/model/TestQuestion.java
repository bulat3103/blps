package com.example.app.model;

import com.example.app.model.ids.TestQuestionId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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
