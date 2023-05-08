package com.example.app.model.ids;

import com.example.app.model.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
