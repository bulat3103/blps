package com.example.app.repositories;

import com.example.app.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select * from comments where test_id = :testId", nativeQuery = true)
    List<Comment> getAllByTestId(@Param("testId") Long testId);
}
