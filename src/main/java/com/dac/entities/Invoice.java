package com.dac.entities;

import java.time.LocalDateTime;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity

@AttributeOverride(name="id", column= @Column(name="invoice_id"))


public class Invoice extends BaseEntity
{
	//invoice_id(pk),appointment_id(fk),total,coupon_discount,final_amount,date_time
	
	@Column(nullable = false ,name="total")
	private float Total; 
	
	@Column(name="coupon_discount",nullable = false)
	private int CouponDiscount;
	
	@Column(name="final_amount",nullable = false)
	private float FinalAmount;
	
	@Column(name="date_time")
	private LocalDateTime Datetime;
	
	@OneToOne
	@JoinColumn(name="appointment_id")
	private Appointment appointment;
		
	
	

}
