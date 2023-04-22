package com.example.blps.model.dto;

import com.example.blps.model.TestCreateStatus;
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

    public static TestStatusDTO toDto(TestCreateStatus status) {
        return new TestStatusDTO(status.getId(), status.getTestName(), status.getStatus());
    }
}
