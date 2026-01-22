package com.Glamify.services;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.CustomerRegDTO;

public interface CustomerService {

    ApiResponse registerCustomer(CustomerRegDTO request);
}
