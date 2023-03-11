package com.example.blps.model;

import com.example.blps.model.ids.TestQuestionId;
import javax.persistence.*;
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
