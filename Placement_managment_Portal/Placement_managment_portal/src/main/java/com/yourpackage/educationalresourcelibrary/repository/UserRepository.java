package com.yourpackage.educationalresourcelibrary.repository;

import com.yourpackage.educationalresourcelibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email); // New method to find user by email
    
}
