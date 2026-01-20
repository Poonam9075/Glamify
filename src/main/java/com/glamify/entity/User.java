package com.glamify.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;
    
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role userRole; // ADMIN, CUSTOMER, PROFESSIONAL
    
    private boolean active = true;
}
