package com.epam.projects.gym.application.service;

import java.util.List;

import com.epam.projects.gym.application.dto.response.TrainingTypeResponse;

public interface TrainingTypeService {

	public List<TrainingTypeResponse> getAllTrainingTypes();
	
}
