package com.Glamify.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Glamify.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //  Catch-all Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("FAILED", e.getMessage()));
    }

    //  Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(
            ResourceNotFoundException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("FAILED", e.getMessage()));
    }

    //  Username not found (Spring Security)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUsernameNotFoundException(
            UsernameNotFoundException e) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse("FAILED", e.getMessage()));
    }

    //  Authentication failure
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse> handleAuthenticationException(
            AuthenticationException e) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse("FAILED", e.getMessage()));
    }
    
    
    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ApiResponse> handleInvalidOperationException(
            InvalidOperationException e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse("FAILED", e.getMessage()));
    }


    //  Validation errors (only if @Valid is used)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {

        List<FieldError> fieldErrors = e.getFieldErrors();

        Map<String, String> errorMap = fieldErrors.stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (msg1, msg2) -> msg1 + " , " + msg2
                ));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorMap);
    }
  
    
}
