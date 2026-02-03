package com.glamify.dto.mapper;

import lombok.Data;

@Data
public class AdminStatsDto {

	long pendingProfessionals;
	long todaysBookings;
	long activeServices;
	long totalUsers;
}
