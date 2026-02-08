package com.Glamify.services;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.ServiceCreateDTO;

public interface AdminService {

    ApiResponse updateService(Long serviceId, ServiceCreateDTO dto);

}
