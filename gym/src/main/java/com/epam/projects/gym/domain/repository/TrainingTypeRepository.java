package com.epam.projects.gym.domain.repository;

import java.util.Optional;

import com.epam.projects.gym.domain.entity.TrainingType;

public interface TrainingTypeRepository {
	
	public Optional<TrainingType> findByName(String trainingTypeName);

}
