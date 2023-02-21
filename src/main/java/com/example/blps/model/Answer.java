package com.example.blps.model;

import com.example.blps.model.ids.AnswerId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "answer")
@NoArgsConstructor
public class Answer {
    @EmbeddedId
    private AnswerId id;

    @Column(name = "rate")
    private Integer rate;

    public Answer(AnswerId id, Integer rate) {
        this.id = id;
        this.rate = rate;
    }
}
