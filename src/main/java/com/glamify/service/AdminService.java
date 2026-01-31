package com.glamify.service;

import java.util.List;

import com.glamify.dto.PendingProfessionalResponse;
import com.glamify.dto.UserDto;
import com.glamify.entity.User;

public interface AdminService {

    List<PendingProfessionalResponse> getPendingProfessionals();

    void approveProfessional(Long professionalId);

    void deactivateUser(Long userId);

	List<UserDto> getAllUsers();

	String updateUser(Long id, UserDto updatedUser);

	User toggleUser(Long id);
}
