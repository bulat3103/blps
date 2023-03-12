package com.example.blps.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "test_result")
@AllArgsConstructor
@NoArgsConstructor
public class TestResult {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private Test testId;

    @Column(name = "left_bound")
    private Integer leftBound;

    @Column(name = "right_bound")
    private Integer rightBound;

    @Column(name = "description")
    private String description;

    public TestResult(Test testId, Integer leftBound, Integer rightBound, String description) {
        this.testId = testId;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.description = description;
    }
}
