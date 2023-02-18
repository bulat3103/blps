package com.example.blps.repositories;

import com.example.blps.model.Comment;
import com.example.blps.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
