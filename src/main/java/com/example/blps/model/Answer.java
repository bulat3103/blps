package com.example.blps.model;

import com.example.blps.model.ids.AnswerId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "answer")
@NoArgsConstructor
public class Answer {
    @EmbeddedId
    private AnswerId id;

    @Column(name = "text")
    private String text;

    @Column(name = "rate")
    private Integer rate;

    public Answer(AnswerId id, String text, Integer rate) {
        this.id = id;
        this.text = text;
        this.rate = rate;
    }
}
