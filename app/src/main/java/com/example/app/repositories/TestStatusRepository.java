package com.example.app.repositories;

import com.example.app.model.TestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestStatusRepository extends JpaRepository<TestStatus, Long> {
    @Query(value = "select * from test_status where user_id = :userId", nativeQuery = true)
    List<TestStatus> getAllByOwner(@Param("userId") Long userId);

    @Query(value = "select * from test_status where status = :status", nativeQuery = true)
    List<TestStatus> getAllByStatus(@Param("status") String status);
}
