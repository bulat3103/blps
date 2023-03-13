package com.example.blps.model;

import com.example.blps.model.dto.TestDTO;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "tests")
@NoArgsConstructor
public class Test {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "points_sum")
    private int pointsSum;

    @Column(name = "points_count")
    private int pointsCount;

    @Column(name = "rating")
    private double rating;

    public static TestDTO toDto(Test test) {
        return new TestDTO(test.getId(), test.getName(),
                test.getPointsCount() == 0 ? 0 : test.getPointsSum() * 1.0 / test.getPointsCount());
    }

    public Test(String name) {
        this.name = name;
    }
}
