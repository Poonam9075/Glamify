package com.glamify.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.glamify.entity.BeautyService;
import com.glamify.repository.BeautyServiceRepository;

@Service
public class BeautyServicesServiceImpl implements BeautyServicesService {

	private final BeautyServiceRepository beautyServiceRepository;

    public BeautyServicesServiceImpl(BeautyServiceRepository beautyServiceRepository) {
        this.beautyServiceRepository = beautyServiceRepository;
    }
	
    @Override
    public List<BeautyService> getActiveServices() {
        return beautyServiceRepository.findByActiveTrue();
    }
    
    @Override
    public List<BeautyService> getAllServices() {
        return beautyServiceRepository.findAll();
    }
    
    @Override
    public BeautyService addService(BeautyService service) {
        return beautyServiceRepository.save(service);
    }
    
    @Override
    public BeautyService updateService(Long id, BeautyService updated) {

        BeautyService existing = beautyServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        existing.setName(updated.getName());
        existing.setCategory(updated.getCategory());
        existing.setPrice(updated.getPrice());
        existing.setDiscount(updated.getDiscount());
        existing.setDuration(updated.getDuration());

        beautyServiceRepository.save(existing);
        
        return existing;
    }
    
    @Override
    public BeautyService toggleService(Long id) {

        BeautyService beautyService = beautyServiceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Service not found"));

        beautyService.setActive(!beautyService.isActive());
        beautyServiceRepository.save(beautyService);

        return beautyService;
    }
}
