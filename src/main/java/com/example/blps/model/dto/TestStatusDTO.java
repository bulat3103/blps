package com.example.blps.model.dto;

import com.example.blps.model.TestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestStatusDTO {
    private Long id;
    private String testName;
    private String status;

    public static TestStatusDTO toDto(TestStatus test) {
        return new TestStatusDTO(test.getId(), test.getTestName(), test.getStatus());
    }
}
