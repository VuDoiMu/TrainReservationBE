package com.example.train_ticket_management.repository;

import com.example.train_ticket_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserName(String userName);
    boolean existsByUserEmail(String email);

    Optional<User> findUserByUserName(String username);
}
