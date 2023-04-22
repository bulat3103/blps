package com.example.blps.repositories;

import com.example.blps.model.TestCreateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestStatusRepository extends JpaRepository<TestCreateStatus, Long> {

    @Query(value = "select * from test_create_status where user_id = :userId", nativeQuery = true)
    List<TestCreateStatus> getAllByUser(@Param("userId") Long userId);
}
