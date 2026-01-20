package com.glamify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.glamify.entity.BeautyService;

public interface BeautyServiceRepository extends JpaRepository<BeautyService, Long> {

	List<BeautyService> findByActiveTrue();
}

