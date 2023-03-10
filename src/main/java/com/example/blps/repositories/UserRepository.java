package com.example.blps.repositories;

import com.example.blps.model.Role;
import com.example.blps.model.User;
import com.example.blps.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);

    User findUserByEmail(String email);

    Boolean existsByEmail(String email);

    List<User> findAllByRoleName(RoleName role);

    default Role findRoleByEmail(String email) {
        User user = findUserByEmail(email);
        return user.getRole();
    }
}
