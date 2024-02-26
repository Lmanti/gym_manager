package com.epam.projects.gym.domain.repository;

import java.util.List;
import java.util.Optional;

import com.epam.projects.gym.application.specification.TrainingSpecification;
import com.epam.projects.gym.domain.entity.Training;

public interface TrainingRepository {

	public Optional<Training> createTraining(Training newTraining);

	public List<Training> findBySpecification(TrainingSpecification specification);

}
