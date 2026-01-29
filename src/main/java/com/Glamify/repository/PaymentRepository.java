package com.Glamify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Glamify.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> 
{
}