package com.glamify.service;

import com.glamify.dto.PaymentDto;

public interface PaymentService {

	PaymentDto processPayment(Long appointmentId);


}
