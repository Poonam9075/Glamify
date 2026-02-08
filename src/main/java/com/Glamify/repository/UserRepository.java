package com.Glamify.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Glamify.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

	boolean existsByPhone(String phone);

	boolean existsByEmail(String email);
}
