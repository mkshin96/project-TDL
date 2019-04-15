package com.mugon.repository;

import com.mugon.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(String id);
    User findByPassword(String password);

    User findByEmail(String email);
}
