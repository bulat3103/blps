package com.example.blps.model;

import com.example.blps.model.dto.TestCommentsDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private Test testId;

    @Column(name = "write_date")
    private Timestamp writeDate;

    @Column(name = "writer")
    private String writer;

    @Column(name = "comment")
    private String comment;

    public Comment(Test testId, Timestamp writeDate, String writer, String comment) {
        this.testId = testId;
        this.writeDate = writeDate;
        this.writer = writer;
        this.comment = comment;
    }

    public static TestCommentsDTO toDTO(Comment comment) {
        return new TestCommentsDTO(comment.id, comment.writeDate,
                comment.writer == null ? "Аноним" : comment.writer, comment.comment);
    }
}
