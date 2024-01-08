package com.epam.projects.gym.domain.repository;

import com.epam.projects.gym.domain.entity.TrainingType;

public interface TrainingTypeRepository {
	
	public TrainingType findByName(String trainingTypeName);

}
