package com.example.blps.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCommentsDTO {
    private Long id;
    private Timestamp writeDate;
    private String writer;
    private String comment;
}
