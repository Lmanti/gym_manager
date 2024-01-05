package com.epam.projects.gym.infrastructure.adapter;

import java.util.List;
import java.util.UUID;

import com.epam.projects.gym.application.dto.basics.TrainingBasicDto;
import com.epam.projects.gym.application.dto.requests.TrainingCreate;

/**
 * Interface for training service.
 * @author lherreram
 *
 */
public interface TrainingAdapter {
	
	/**
	 * Retrieves all incoming trainees's IDs from db.
	 * @return List with all trainings.
	 */
	public List<TrainingBasicDto> getAllByIds(List<UUID> ids);
	
	/**
	 * Retrieves a trainees by IDs from db.
	 * @return the found Training.
	 */
	public TrainingBasicDto getById(UUID id);
	
	/**
	 * Retrieves all Trainings from db.
	 * @return List with all Trainings.
	 */
	public List<TrainingBasicDto> getAllTraining();
	
	/**
	 * Create a new Training.
	 * @param Training
	 * 		- DTO with the Training info to register.
	 * @return boolean - true if created, false if not.
	 */
	public boolean createTraining(TrainingCreate training);

}
