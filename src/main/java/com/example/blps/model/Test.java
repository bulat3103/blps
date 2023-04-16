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

    @Column(name = "rating")
    private double rating;

    public static TestDTO toDto(Test test) {
        return new TestDTO(test.getId(), test.getName(), test.getRating());
    }

    public Test(String name, double rating) {
        this.name = name;
        this.rating = rating;
    }
}
