package com.glamify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentGatewayResponse {

    private boolean success;
    private String transactionId;
    private String failureReason;

    public static PaymentGatewayResponse success(String txnId) {
        PaymentGatewayResponse r = new PaymentGatewayResponse();
        r.success = true;
        r.transactionId = txnId;
        return r;
    }

    public static PaymentGatewayResponse failure(String reason) {
        PaymentGatewayResponse r = new PaymentGatewayResponse();
        r.success = false;
        r.failureReason = reason;
        return r;
    }

    // getters
}
