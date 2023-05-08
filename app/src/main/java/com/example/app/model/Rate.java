package com.example.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "rates")
@NoArgsConstructor
public class Rate {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private Test testId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "rate")
    private Integer rate;

    public Rate(Test testId, User userId, Timestamp date, Integer rate) {
        this.testId = testId;
        this.userId = userId;
        this.date = date;
        this.rate = rate;
    }
}
