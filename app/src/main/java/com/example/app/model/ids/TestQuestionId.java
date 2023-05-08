package com.example.app.model.ids;

import com.example.app.model.Question;
import com.example.app.model.Test;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class TestQuestionId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test testId;

    @ManyToOne
    @JoinColumn(name = "q_id")
    private Question qId;

    public TestQuestionId(Test testId, Question qId) {
        this.testId = testId;
        this.qId = qId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestQuestionId)) return false;
        TestQuestionId that = (TestQuestionId) o;
        return Objects.equals(getTestId(), that.getTestId()) && Objects.equals(qId, that.qId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTestId(), qId);
    }
}
