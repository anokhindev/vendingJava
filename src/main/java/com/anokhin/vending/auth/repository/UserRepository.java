package com.anokhin.vending.auth.repository;

import com.anokhin.vending.auth.entity.Role;
import com.anokhin.vending.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}