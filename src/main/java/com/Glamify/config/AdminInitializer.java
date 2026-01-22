package com.Glamify.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Glamify.entities.Gender;
import com.Glamify.entities.Status;
import com.Glamify.entities.User;
import com.Glamify.entities.UserRole;
import com.Glamify.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner createAdminUser() {
        return args -> {

            // check if admin already exists
            if (userRepository.existsByEmail("admin@glamify.com")) {
                return;
            }

            User admin = new User();
            admin.setFirstName("System");
            admin.setLastName("Admin");
            admin.setEmail("admin@glamify.com");
            admin.setPhone("9999999999");
            admin.setGender(Gender.FEMALE); // optional
            admin.setPassword(passwordEncoder.encode("admin@123"));
            admin.setRole(UserRole.ROLE_ADMIN);
            admin.setIsActive(Status.ACTIVE);

            userRepository.save(admin);

            System.out.println("Admin user created successfully");
        };
    }
}
