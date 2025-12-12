package com.dac.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
@AttributeOverride(name = "id", column = @Column(name ="customer_id"))
public class Customer extends BaseEntity {
	Gender gender;
	
	@OneToOne(cascade= CascadeType.ALL) // mandatory - o.w Hibernate throws - MappingException
	//To specify FK column name & to add not null constraint
	@JoinColumn(name = "uesr_id", nullable = false)
	private User userDetails;
}
