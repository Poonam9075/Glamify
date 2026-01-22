package com.Glamify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Glamify.entities.Services;


public interface ServicesRepository 
        extends JpaRepository<Services, Long> {
}
