package com.Glamify.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.OtpVerifyDTO;
import com.Glamify.entities.User;
import com.Glamify.services.OtpService;
import com.Glamify.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.Glamify.dto.ApiResponse;
//import com.Glamify.dto.OtpVerifyDTO;
//import com.Glamify.services.OtpService;
//import com.Glamify.services.UserService;
//
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequestMapping("/otp")
//@RequiredArgsConstructor
//public class OtpController {
//
//    private final OtpService otpService;
//    private final UserService userService;
//
//    @PostMapping("/verify")
//    public ResponseEntity<ApiResponse> verifyOtp(
//            @RequestBody OtpVerifyDTO dto) {
//
//        otpService.verifyOtp(dto.getEmail(), dto.getOtp());
//
//        userService.activateUser(dto.getEmail());
//
//        return ResponseEntity.ok(
//                new ApiResponse("SUCCESS", "Account verified successfully")
//        );
//    }
//}

@RestController
@RequestMapping("/auth/otp")
@RequiredArgsConstructor
public class OtpController {

    private final OtpService otpService;
    private final UserService userService;

  



@PostMapping("/verify")
public ResponseEntity<ApiResponse> verifyOtp(@Valid @RequestBody OtpVerifyDTO request) {

    // 1. Verify (Should throw exception if invalid)
    otpService.verifyOtp(request.getEmail(), request.getOtp());

    // 2. Fetch
    User user = userService.getUserByEmail(request.getEmail());

    // 3. Activate
    userService.activateUser(user.getId());

    return ResponseEntity.ok(
            new ApiResponse("SUCCESS", "Account activated successfully")
    );
}}
