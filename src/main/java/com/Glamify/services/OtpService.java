package com.Glamify.services;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.Glamify.entities.Otp;
import com.Glamify.exceptions.InvalidOperationException;
import com.Glamify.repository.OtpRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OtpService {

    private final OtpRepository otpRepository;
    private final EmailService emailService;

    public void generateAndSendOtp(String email) {

        String otp = String.valueOf(
                new Random().nextInt(900000) + 100000);

        Otp otpEntity = new Otp();
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(
                LocalDateTime.now().plusMinutes(5));
        otpEntity.setVerified(false);

        otpRepository.save(otpEntity);

        emailService.sendEmail(
                email,
                "Glamify OTP Verification",
                "Your OTP is: " + otp
        );
    }

    public void verifyOtp(String email, String otp) {

        Otp otpEntity = otpRepository
                .findByEmailAndOtpAndVerifiedFalse(email, otp)
                .orElseThrow(() ->
                        new InvalidOperationException("Invalid OTP"));

        if (otpEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new InvalidOperationException("OTP expired");
        }

        otpEntity.setVerified(true);
    }
}

