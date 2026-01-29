package com.Glamify.services;

import com.Glamify.dto.PaymentRequestDto;
import com.Glamify.dto.PaymentResponseDto;

public interface PaymentService {

    PaymentResponseDto makePayment(PaymentRequestDto request);
}
