package com.Glamify.entities;

import java.sql.Time;

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
@AttributeOverride(name = "id", column = @Column(name ="service_id"))
public class Services extends BaseEntity{
 
	@Column(length = 50, nullable = false)	
	 private String serviceName;
	
	@Column(nullable = false)
	 private int price;
	
	 private Time estimatedTime;
	 
	 @ManyToOne 
	 @JoinColumn(name = "serviceCategory_id", nullable = false)
	 private ServiceCategory category;
	 
	 @Enumerated(EnumType.STRING)
	 private Gender gender;
}
