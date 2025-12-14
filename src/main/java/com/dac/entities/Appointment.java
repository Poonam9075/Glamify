package com.dac.entities;

import java.time.LocalDateTime;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity

@AttributeOverride(name= "id" , column = @Column(name="appointment_id"))     //change column name 

public class Appointment  extends BaseEntity

{
	//datetime , location, status,customer_id(fk),professional_id(fk)
	
	@Column(length = 100,nullable = false)
	private String location;
	
	@Column(nullable = false ,name="date_time")
	private LocalDateTime  dateTime;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ManyToOne
	@JoinColumn(name= "customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="professional_id")
	private Professional professional;
	

}
