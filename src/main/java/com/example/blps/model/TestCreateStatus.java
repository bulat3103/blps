package com.example.blps.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "test_create_status")
@NoArgsConstructor
public class TestCreateStatus {
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

    public TestCreateStatus(User userId, String status, String testName) {
        this.userId = userId;
        this.status = status;
        this.testName = testName;
    }
}
