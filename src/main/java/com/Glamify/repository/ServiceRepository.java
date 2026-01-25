package com.Glamify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Glamify.entities.Gender;
import com.Glamify.entities.Services;


public interface ServiceRepository 
        extends JpaRepository<Services, Long> {
	
	boolean existsByServiceName(String serviceName);
	
	// Search by name (case-insensitive)
	List<Services> findByServiceNameContainingIgnoreCase(String serviceName);
	
	// Search by gender
	List<Services> findByGender(Gender gender);
	
	// Filter services by price range
	List<Services> findByPriceBetween(Double minPrice, Double maxPrice);
}
