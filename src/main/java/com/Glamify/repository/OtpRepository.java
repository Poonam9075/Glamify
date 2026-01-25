package com.Glamify.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Glamify.entities.Otp;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findByEmailAndOtpAndVerifiedFalse(
            String email, String otp);
}
