package com.glamify.dto.mapper;

import com.glamify.dto.PaymentDto;
import com.glamify.entity.Payment;

public class PaymentMapper {

	public static PaymentDto toDto(Payment payment)
	{
		PaymentDto paymentDto = new PaymentDto();
		
		paymentDto.setAmount(payment.getAppointment().getAmount());
		paymentDto.setAppointmentId(payment.getAppointment().getAppointmentId());
		//paymentDto.setCurrency(payment.getInvoice().get);
		if(payment.getInvoice() != null)
			paymentDto.setInvoiceNumber(payment.getInvoice().getInvoiceNumber());
		//paymentDto.setPaidAt(payment.get);
		paymentDto.setPaymentId(payment.getPaymentId());
		paymentDto.setPaymentStatus(payment.getStatus().toString());
		paymentDto.setTransactionId(payment.getTransactionId());
		
		return paymentDto;
	}
	
}
