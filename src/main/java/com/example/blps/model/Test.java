package com.example.blps.model;

import com.example.blps.model.dto.TestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tests")
public class Test {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "points_sum")
    private Integer pointsSum;

    @Column(name = "points_count")
    private Integer pointsCount;

    public static TestDTO toDto(Test test) {
        return new TestDTO(test.getId(), test.getName(),
                test.getPointsCount() == 0 ? 0 : test.getPointsSum() * 1.0 / test.getPointsCount());
    }
}
