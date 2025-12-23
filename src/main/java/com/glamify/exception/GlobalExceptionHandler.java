package com.glamify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // =========================================
    // HANDLE INVALID APPOINTMENT STATUS
    // =========================================
    @ExceptionHandler(InvalidAppointmentStatusException.class)
    public ResponseEntity<?> handleInvalidStatus(
            InvalidAppointmentStatusException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "error", ex.getMessage()
                ));
    }

    // =========================================
    // HANDLE ALL OTHER RUNTIME EXCEPTIONS
    // =========================================
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "error", ex.getMessage()
                ));
    }
}
