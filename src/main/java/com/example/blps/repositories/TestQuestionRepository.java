package com.example.blps.repositories;

import com.example.blps.model.TestQuestion;
import com.example.blps.model.ids.TestQuestionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TestQuestionRepository extends JpaRepository<TestQuestion, TestQuestionId> {

    @Query(value = "select q_id from test_question where test_id = :testId and number = :qNumber", nativeQuery = true)
    Long getByTestIdAndNumber(@Param("testId") Long testId, @Param("qNumber") Integer qNumber);

    @Query(value = "select count(*) from test_question where test_id = :testId", nativeQuery = true)
    Integer countByTestId(@Param("testId") Long testId);
}
