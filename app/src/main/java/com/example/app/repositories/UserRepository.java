package com.example.app.repositories;

import com.example.app.model.Role;
import com.example.app.model.User;
import com.example.app.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
