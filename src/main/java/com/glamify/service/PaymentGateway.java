package com.glamify.service;

import com.glamify.dto.PaymentGatewayRequest;
import com.glamify.dto.PaymentGatewayResponse;

public interface PaymentGateway {

    PaymentGatewayResponse processPayment(PaymentGatewayRequest request);
}
