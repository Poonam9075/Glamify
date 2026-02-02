package com.glamify.service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;
import com.glamify.dto.PaymentGatewayRequest;
import com.glamify.dto.PaymentGatewayResponse;

@Component
public class DummyPaymentGateway implements PaymentGateway {

    private static final AtomicInteger counter = new AtomicInteger();

    @Override
    public PaymentGatewayResponse processPayment(PaymentGatewayRequest request) {

        int attempt = counter.incrementAndGet();

        if (attempt % 2 == 0) {
            return PaymentGatewayResponse.failure(
                    "Simulated gateway failure"
            );
        }

        return PaymentGatewayResponse.success(
                UUID.randomUUID().toString()
        );
    }

}
