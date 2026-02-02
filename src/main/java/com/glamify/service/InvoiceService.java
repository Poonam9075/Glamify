package com.glamify.service;

import com.glamify.entity.Appointment;
import com.glamify.entity.Invoice;
import com.glamify.entity.Payment;

public interface InvoiceService {

	Invoice generateInvoice(Appointment appointment, Payment payment);
	
	byte[] generateInvoicePdf(String invoiceNumber);

}
