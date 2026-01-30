package com.glamify.service;

import java.util.List;
import com.glamify.entity.BeautyService;

public interface BeautyServicesService {

	List<BeautyService> getActiveServices();

	List<BeautyService> getAllServices();

	BeautyService addService(BeautyService service);

	BeautyService updateService(Long id, BeautyService updated);

	BeautyService toggleService(Long id);

}
