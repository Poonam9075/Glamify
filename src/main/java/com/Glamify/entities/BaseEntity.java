package com.Glamify.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/*
 * Common Base Class, w/o any table associated with it
 * Common Fields here.
 */

//do not create entity
@MappedSuperclass
public abstract class BaseEntity {

//Primary key
	@Id
//Auto Increment
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	 
//To specify creation date time
	@CreationTimestamp
	private LocalDateTime createdOn;
	
// To specify updation date time
	@UpdateTimestamp
	private LocalDateTime lastUpdated;
	
	}

