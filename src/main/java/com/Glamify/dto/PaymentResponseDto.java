package com.Glamify.dto;

import com.Glamify.entities.PaymentStatus;

public class PaymentResponseDto {

    private String message;
    private PaymentStatus status;

    public PaymentResponseDto() {
    }

    public PaymentResponseDto(String message, PaymentStatus status) {
        this.message = message;
        this.status = status;
    }

    /* ===== GETTERS ===== */

    public String getMessage() {
        return message;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    /* ===== SETTERS ===== */

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}