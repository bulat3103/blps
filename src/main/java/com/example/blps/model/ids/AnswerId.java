package com.example.blps.model.ids;

import com.example.blps.model.Question;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class AnswerId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "q_id")
    private Question qId;

    @Column(name = "ans_num")
    private Integer ansNum;

    public AnswerId(Question qId, Integer ansNum) {
        this.qId = qId;
        this.ansNum = ansNum;
    }
}
