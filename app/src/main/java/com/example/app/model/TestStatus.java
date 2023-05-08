package com.example.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "test_status")
@NoArgsConstructor
public class TestStatus {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "test_name", nullable = false)
    private String testName;

    @Column(name = "test_json", nullable = false)
    private String testJson;

    public TestStatus(User userId, String status, String testName, String testJson) {
        this.userId = userId;
        this.status = status;
        this.testName = testName;
        this.testJson = testJson;
    }
}