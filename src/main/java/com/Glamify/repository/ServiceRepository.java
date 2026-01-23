package com.Glamify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Glamify.entities.Services;


public interface ServiceRepository 
        extends JpaRepository<Services, Long> {
	boolean existsByServiceName(String serviceName);
}
