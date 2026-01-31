package com.glamify.service;

import java.util.List;
import com.glamify.dto.BeautyServiceDto;

public interface BeautyServicesService {

	List<BeautyServiceDto> getActiveServices();

	List<BeautyServiceDto> getAllServices();

	BeautyServiceDto addService(BeautyServiceDto beautyServiceDto);

	BeautyServiceDto updateService(Long id, BeautyServiceDto updated);

	BeautyServiceDto toggleService(Long id);

}
