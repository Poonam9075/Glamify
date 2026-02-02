package com.glamify.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentGatewayRequest {

    private Long appointmentId;
    private double amount;
    private String currency;

    // constructors, getters, setters
}
