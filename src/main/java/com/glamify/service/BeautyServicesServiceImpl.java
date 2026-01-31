package com.glamify.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.glamify.dto.BeautyServiceDto;
import com.glamify.dto.mapper.BeautyServiceMapper;
import com.glamify.entity.BeautyService;
import com.glamify.repository.BeautyServiceRepository;

@Service
public class BeautyServicesServiceImpl implements BeautyServicesService {

	private final BeautyServiceRepository beautyServiceRepository;

    public BeautyServicesServiceImpl(BeautyServiceRepository beautyServiceRepository) {
        this.beautyServiceRepository = beautyServiceRepository;
    }
	
    @Override
    public List<BeautyServiceDto> getActiveServices() {
    	
        return BeautyServiceMapper.toDtoList(beautyServiceRepository.findByActiveTrue());
    }
    
    @Override
    public List<BeautyServiceDto> getAllServices() {
        return BeautyServiceMapper.toDtoList(beautyServiceRepository.findAll());
    }
    
    @Override
    public BeautyServiceDto addService(BeautyServiceDto beautyServiceDto) {
    	
    	BeautyService beautyService = BeautyServiceMapper.toEntity(beautyServiceDto);
    	
        return BeautyServiceMapper.toDto(beautyServiceRepository.save(beautyService));
    }
    
    @Override
    public BeautyServiceDto updateService(Long id, BeautyServiceDto updated) {

        BeautyService existing = beautyServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        existing.setName(updated.getName());
        existing.setCategory(updated.getCategory());
        existing.setPrice(updated.getPrice());
        existing.setDiscount(updated.getDiscount());
        existing.setDuration(updated.getDuration());

        beautyServiceRepository.save(existing);
        
        return BeautyServiceMapper.toDto(existing);
    }
    
    @Override
    public BeautyServiceDto toggleService(Long id) {

        BeautyService beautyService = beautyServiceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Service not found"));

        beautyService.setActive(!beautyService.isActive());
        beautyServiceRepository.save(beautyService);

        return BeautyServiceMapper.toDto(beautyService);
    }
}
