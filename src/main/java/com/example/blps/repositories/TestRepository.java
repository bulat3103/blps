package com.example.blps.repositories;

import com.example.blps.model.Test;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long>{
}
