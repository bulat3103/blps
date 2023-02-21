package com.example.blps.repositories;

import com.example.blps.model.Answer;
import com.example.blps.model.ids.AnswerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, AnswerId> {

    @Query(value = "select rate from answer where q_id = :qId and ans_num = :ansNum", nativeQuery = true)
    Integer getRateByQuestionAndAnsNum(@Param("qId") Long qId, @Param("ansNum") Integer ansNum);
}
