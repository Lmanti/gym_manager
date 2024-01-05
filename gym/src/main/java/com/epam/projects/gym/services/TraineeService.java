package com.epam.projects.gym.services;

import java.util.List;
import java.util.UUID;

import com.epam.projects.gym.dto.basics.TraineeBasicDto;
import com.epam.projects.gym.dto.requests.TraineeRegister;
import com.epam.projects.gym.dto.requests.TraineeUpdate;

/**
 * Interface for trainee service.
 * @author lherreram
 *
 */
public interface TraineeService {
	
	/**
	 * Retrieves all trainees from db.
	 * @return List with all trainees.
	 */
	public List<TraineeBasicDto> getAllTrainees();
	
	/**
	 * Get a trainee by ID from the db.
	 * @param id
	 * 		- Trainee's ID to search.
	 * @return the found trainee.
	 */
	public TraineeBasicDto getTraineeById(UUID id);
	
	/**
	 * Get a trainee by username from the db.
	 * @param username
	 * 		- Trainee's username to search.
	 * @return the found trainee.
	 */
	public TraineeBasicDto getTraineeByUsername(String username);

	/**
	 * Create a new trainee.
	 * @param trainee
	 * 		- DTO with the Trainee info to register.
	 * @return the created trainee.
	 */
	public TraineeBasicDto createTrainee(TraineeRegister trainee);
	
	/**
	 * Update a trainee.
	 * @param update
	 * 		- DTO with the new trainee info to update. 
	 * @return the updated trainee.
	 */
	public TraineeBasicDto updateTrainee(TraineeUpdate update);
	
	/**
	 * Delete a trainee by ID.
	 * @param id
	 * 		- Trainee's ID to delete.
	 * @return true if done, false if not.
	 */
	public boolean deleteTrainee(UUID id);

	public List<TraineeBasicDto> getAllByIds(List<UUID> trainees);
	
	public boolean changeTraineePassword(String username, String newPasword);
}
