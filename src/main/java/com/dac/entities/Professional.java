package com.dac.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@AttributeOverride(name = "id", column= @Column(name = "professional_id"))
public class Professional extends BaseEntity{
	//(speciality, gender, experience_in_years)
	
	@Column(length = 30)
	private String speciality;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private int experience_in_years;
	//ratings--------

}
