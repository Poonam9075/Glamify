package com.glamify.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.glamify.entity.BeautyService;
import com.glamify.entity.Customer;
import com.glamify.entity.Gender;
import com.glamify.entity.Professional;
import com.glamify.entity.Role;
import com.glamify.entity.User;
import com.glamify.repository.BeautyServiceRepository;
import com.glamify.repository.CustomerRepository;
import com.glamify.repository.ProfessionalRepository;
import com.glamify.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createDefaultAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
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
    
    @Bean
    CommandLineRunner createCustomer(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            String email = "riya@gmail.com";

            // check if admin already exists
            if (customerRepository.findByEmail(email).isPresent()) {
                return;
            }

            Customer customer = new Customer();
            customer.setFullName("Riya Sharma");
            customer.setEmail(email);
            customer.setPhone("9966554433");
            customer.setGender(Gender.FEMALE);
            customer.setRole(Role.CUSTOMER);
            customer.setActive(true);
            customer.setPassword(passwordEncoder.encode("riya123"));

            customerRepository.save(customer);

            System.out.println("✅ Customer "+ email +" created");
        };
    }
    
    @Bean
    CommandLineRunner createProfessional(ProfessionalRepository professionalRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            String email = "sneha@gmail.com";

            // check if admin already exists
            if (professionalRepository.findByEmail(email).isPresent()) {
                return;
            }

            Professional professional = new Professional();
            professional.setFullName("Shena Verma");
            professional.setEmail(email);
            professional.setPhone("9966557777");
            professional.setGender(Gender.FEMALE);
            professional.setRole(Role.PROFESSIONAL);
            professional.setActive(true);
            professional.setPassword(passwordEncoder.encode("sneha123"));
            
            professional.setExperienceInYears(3);
            professional.setSpeciality("Hair");
            professional.setRating(0);
            professional.setApproved(true);

            professionalRepository.save(professional);

            System.out.println("✅ Customer "+ email +" created");
        };
    }
    
    @Bean
    CommandLineRunner createServices(BeautyServiceRepository beautyServiceRepository) {
        return args -> {
        	
        	BeautyService bs;
        	
        	if (!beautyServiceRepository.findByName("Hair Spa").isPresent()) {
        	    bs = new BeautyService();
        	    bs.setActive(true);
        	    bs.setCategory("Hair");
        	    bs.setDiscount(5);
        	    bs.setDuration(90);
        	    bs.setName("Hair Spa");
        	    bs.setPrice(1200);
        	    beautyServiceRepository.save(bs);
        	}

        	if (!beautyServiceRepository.findByName("Hair Coloring").isPresent()) {
        	    bs = new BeautyService();
        	    bs.setActive(true);
        	    bs.setCategory("Hair");
        	    bs.setDiscount(8);
        	    bs.setDuration(120);
        	    bs.setName("Hair Coloring");
        	    bs.setPrice(2500);
        	    beautyServiceRepository.save(bs);
        	}

        	if (!beautyServiceRepository.findByName("Hair Straightening").isPresent()) {
        	    bs = new BeautyService();
        	    bs.setActive(true);
        	    bs.setCategory("Hair");
        	    bs.setDiscount(10);
        	    bs.setDuration(180);
        	    bs.setName("Hair Straightening");
        	    bs.setPrice(4500);
        	    beautyServiceRepository.save(bs);
        	}

        	if (!beautyServiceRepository.findByName("Party Makeup").isPresent()) {
        	    bs = new BeautyService();
        	    bs.setActive(true);
        	    bs.setCategory("Makeup");
        	    bs.setDiscount(5);
        	    bs.setDuration(120);
        	    bs.setName("Party Makeup");
        	    bs.setPrice(2500);
        	    beautyServiceRepository.save(bs);
        	}

        	if (!beautyServiceRepository.findByName("Engagement Makeup").isPresent()) {
        	    bs = new BeautyService();
        	    bs.setActive(true);
        	    bs.setCategory("Makeup");
        	    bs.setDiscount(7);
        	    bs.setDuration(150);
        	    bs.setName("Engagement Makeup");
        	    bs.setPrice(3000);
        	    beautyServiceRepository.save(bs);
        	}

        	if (!beautyServiceRepository.findByName("Pedicure").isPresent()) {
        	    bs = new BeautyService();
        	    bs.setActive(true);
        	    bs.setCategory("Nail");
        	    bs.setDiscount(0);
        	    bs.setDuration(90);
        	    bs.setName("Pedicure");
        	    bs.setPrice(900);
        	    beautyServiceRepository.save(bs);
        	}

        	if (!beautyServiceRepository.findByName("Gel Nail Extension").isPresent()) {
        	    bs = new BeautyService();
        	    bs.setActive(true);
        	    bs.setCategory("Nail");
        	    bs.setDiscount(5);
        	    bs.setDuration(120);
        	    bs.setName("Gel Nail Extension");
        	    bs.setPrice(1800);
        	    beautyServiceRepository.save(bs);
        	}

        	if (!beautyServiceRepository.findByName("Full Arms Waxing").isPresent()) {
        	    bs = new BeautyService();
        	    bs.setActive(true);
        	    bs.setCategory("Waxing");
        	    bs.setDiscount(0);
        	    bs.setDuration(45);
        	    bs.setName("Full Arms Waxing");
        	    bs.setPrice(600);
        	    beautyServiceRepository.save(bs);
        	}

        	if (!beautyServiceRepository.findByName("Full Legs Waxing").isPresent()) {
        	    bs = new BeautyService();
        	    bs.setActive(true);
        	    bs.setCategory("Waxing");
        	    bs.setDiscount(0);
        	    bs.setDuration(60);
        	    bs.setName("Full Legs Waxing");
        	    bs.setPrice(800);
        	    beautyServiceRepository.save(bs);
        	}

        	if (!beautyServiceRepository.findByName("Full Body Waxing").isPresent()) {
        	    bs = new BeautyService();
        	    bs.setActive(true);
        	    bs.setCategory("Waxing");
        	    bs.setDiscount(5);
        	    bs.setDuration(120);
        	    bs.setName("Full Body Waxing");
        	    bs.setPrice(3000);
        	    beautyServiceRepository.save(bs);
        	}

        	if (!beautyServiceRepository.findByName("Bridal Package Basic").isPresent()) {
        	    bs = new BeautyService();
        	    bs.setActive(true);
        	    bs.setCategory("Package");
        	    bs.setDiscount(10);
        	    bs.setDuration(300);
        	    bs.setName("Bridal Package Basic");
        	    bs.setPrice(8000);
        	    beautyServiceRepository.save(bs);
        	}

        	if (!beautyServiceRepository.findByName("Bridal Package Premium").isPresent()) {
        	    bs = new BeautyService();
        	    bs.setActive(true);
        	    bs.setCategory("Package");
        	    bs.setDiscount(15);
        	    bs.setDuration(420);
        	    bs.setName("Bridal Package Premium");
        	    bs.setPrice(15000);
        	    beautyServiceRepository.save(bs);
        	}

        	
        };
    }
}
