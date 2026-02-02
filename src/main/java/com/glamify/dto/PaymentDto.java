package com.glamify.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PaymentDto {

	private Long paymentId;
    private Long appointmentId;

    private Double amount;
    private String currency;

    private String paymentStatus;
    private String transactionId;

    private String invoiceNumber;

    private LocalDateTime paidAt;
}
