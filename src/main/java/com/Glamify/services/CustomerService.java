package com.Glamify.services;

import java.util.List;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.CustomerRegDTO;
import com.Glamify.entities.Gender;
import com.Glamify.entities.Services;

public interface CustomerService {

    ApiResponse registerCustomer(CustomerRegDTO request);
    List<Services> searchServices(String serviceName);
    List<Services> searchServicesByGender(Gender gender);
    List<Services> filterServicesByPrice(Double minPrice, Double maxPrice);
	List<Services> getAllServices();
}
