package com.glamify.repository;

import com.glamify.entity.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessionalRepository
        extends JpaRepository<Professional, Long> {

    Optional<Professional> findByEmail(String email);
}
