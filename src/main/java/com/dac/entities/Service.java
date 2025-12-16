package com.dac.entities;

import java.sql.Time;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@AttributeOverride(name = "id", column = @Column(name ="service_id"))
public class Service extends BaseEntity{
 
@Column(length = 50, nullable = false)	
 private String serviceName;

@Column(nullable = false)
 private int price;

 private Time estimatedTime;
 
 @ManyToOne 
 @JoinColumn(name = "serviceCategory_id", nullable = false)
 private ServiceCategory category;
}
