package com.Glamify.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Glamify.entities.Status;
import com.Glamify.entities.User;
import com.Glamify.exceptions.ResourceNotFoundException;
import com.Glamify.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImple implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with email: " + email)
                );
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + userId)
                );
    }

    @Override
    public void activateUser(Long userId) {
        User user = getUserById(userId);

        if (user.getIsActive() == Status.ACTIVE) {
            return;
        }

        user.setIsActive(Status.ACTIVE);
        userRepository.save(user);
    }
}
