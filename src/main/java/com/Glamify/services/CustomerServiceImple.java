package com.Glamify.services;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.CustomerRegDTO;
import com.Glamify.entities.Customer;
import com.Glamify.entities.Status;
import com.Glamify.entities.User;
import com.Glamify.entities.UserRole;
import com.Glamify.exceptions.DuplicateResourceException;
import com.Glamify.repository.CustomerRepository;
import com.Glamify.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImple implements CustomerService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse registerCustomer(CustomerRegDTO request) {

        // ✅ 1. Duplicate Email Check
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already registered: " + request.getEmail());
        }

        // ✅ 2. Duplicate Phone Check
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Phone already registered: " + request.getPhone());
        }

        // ✅ 3. Map DTO → User entity
        User user = modelMapper.map(request, User.class);

        // ✅ 4. Encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // ✅ 5. Assign role & status
        user.setRole(UserRole.ROLE_CUSTOMER);
        user.setIsActive(Status.ACTIVE);

        // ✅ 6. Save User
        User savedUser = userRepository.save(user);

        // ✅ 7. Create Customer entity
        Customer customer = new Customer();
        customer.setUser(savedUser);

        // ✅ 8. Save Customer
        customerRepository.save(customer);

        // ✅ 9. Return response
        return new ApiResponse(
                "SUCCESS",
                "Customer registered successfully"
        );
    }
}
