package com.example.blps.repositories;

import com.example.blps.model.Test;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long>{
    @Query(value = "select * from tests limit :limit offset :offser",nativeQuery = true)
    List<Test> getAllTests(@Param("limit") int limit, @Param("offset") int offset);
}
