package com.Glamify.entities;

import java.time.LocalDateTime;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@AttributeOverride(name = "id", column = @Column(name = "appointment_id"))
public class Appointment extends BaseEntity {

    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
   // private AppointmentStatus status;
    private AppointmentStatus status = AppointmentStatus.BOOKED;

    // -------- Address at booking time --------
    @Column(length = 200, nullable = false)
    private String serviceAddress;

    // -------- Relationships --------

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "professional_id", nullable = false)
    private Professional professional;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private Services service;
    


@Column
private Integer rating; // 1–5


// getter & setter
public Integer getRating() {
return rating;
}


public void setRating(Integer rating) {
this.rating = rating;
}

	public void setStatus(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setStatus(AppointmentStatus cancelled) {
		// TODO Auto-generated method stub
		
	}
}

	