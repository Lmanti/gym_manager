package com.epam.projects.gym.application.service;

import java.util.List;
import java.util.UUID;

import com.epam.projects.gym.application.dto.basic.TrainerBasicDto;
import com.epam.projects.gym.application.dto.request.TrainerRegister;
import com.epam.projects.gym.application.dto.request.TrainerUpdate;

/**
 * Interface for trainer service.
 * @author lherreram
 *
 */
public interface TrainerService {
	
/**
	 * Retrieves all Trainers from db.
	 * @return List with all Trainers.
	 */
	public List<TrainerBasicDto> getAllTrainers();
	
	/**
	 * Get a Trainer by ID from the db.
	 * @param id
	 * 		- Trainer ID to search.
	 * @return the found Trainer.
	 */
	public TrainerBasicDto getTrainerById(UUID id);

	/**
	 * Create a new Trainer.
	 * @param Trainer
	 * 		- DTO with the Trainer info to register.
	 * @return the created Trainer.
	 */
	public TrainerBasicDto createTrainer(TrainerRegister trainer);
	
	/**
	 * Update a Trainer.
	 * @param Trainer
	 * 		- DTO with the Trainer info to update.
	 * @return the updated Trainer.
	 */
	public TrainerBasicDto updateTrainer(TrainerUpdate trainer);

	public TrainerBasicDto getTrainerByUsername(String username);

	public List<TrainerBasicDto> getAllByIds(List<UUID> trainers);
	
	public boolean changeTrainerPassword(String username, String newPasword);

}
