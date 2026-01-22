package com.Glamify.services;

	import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.ProfessionalApprovalDTO;
import com.Glamify.dto.ProfessionalRegDTO;

	public interface ProfessionalService {

	    ApiResponse registerProfessional(ProfessionalRegDTO request);
	    
	    ApiResponse updateProfessionalStatus(Long professionalId,
                ProfessionalApprovalDTO request);

	}


