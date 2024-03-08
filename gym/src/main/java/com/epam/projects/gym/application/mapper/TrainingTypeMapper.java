package com.epam.projects.gym.application.mapper;

import com.epam.projects.gym.application.dto.response.TrainingTypeResponse;
import com.epam.projects.gym.domain.entity.TrainingType;

public class TrainingTypeMapper {
	
	public static final TrainingTypeResponse toResponse(TrainingType trainingType) {
		TrainingTypeResponse response = new TrainingTypeResponse();
		response.setName(trainingType.getName());
		return response;
	}

}
