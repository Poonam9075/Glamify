package com.dac.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long user_id;

	@Column(name = "first_name", length=40,nullable = false)
    private String firstName;
	@Column(name = "last_name", length=40,nullable = false)
    private String lastName;

    @Column(unique = true,nullable = false, length=50)
    private String email;
    
    @Column(unique = true, length=10) // check length later
    private String phone;
    
    @Column(nullable = false, length = 50)
    private String password;
    
    @Enumerated(EnumType.STRING)
    private UserRole role;   // ADMIN, CUSTOMER, PROVIDER
    
}
