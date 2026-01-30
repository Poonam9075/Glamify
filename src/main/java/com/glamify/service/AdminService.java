package com.glamify.service;

import java.util.List;
import com.glamify.dto.PendingProfessionalResponse;
import com.glamify.entity.User;

public interface AdminService {

    List<PendingProfessionalResponse> getPendingProfessionals();

    void approveProfessional(Long professionalId);

    void deactivateUser(Long userId);

	List<User> getAllUsers();

	String updateUser(Long id, User updatedUser);

	User toggleUser(Long id);
}
