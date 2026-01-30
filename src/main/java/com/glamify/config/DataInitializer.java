package com.glamify.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.glamify.entity.Gender;
import com.glamify.entity.Role;
import com.glamify.entity.User;
import com.glamify.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createDefaultAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            String adminEmail = "admin@glamify.com";

            // check if admin already exists
            if (userRepository.findByEmail(adminEmail).isPresent()) {
                return;
            }

            User admin = new User();
            admin.setFullName("System Admin");
            admin.setEmail(adminEmail);
            admin.setPhone("9999999999");
            admin.setGender(Gender.MALE);
            admin.setRole(Role.ADMIN);
            admin.setActive(true);
            admin.setPassword(passwordEncoder.encode("admin123"));

            userRepository.save(admin);

            System.out.println("✅ Default admin user created");
        };
    }
}
