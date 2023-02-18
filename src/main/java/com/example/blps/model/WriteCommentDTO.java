package com.example.blps.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WriteCommentDTO {
    private Long testId;
    private String writer;
    private String comment;
}
