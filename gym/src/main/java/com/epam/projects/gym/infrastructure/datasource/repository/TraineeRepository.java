package com.epam.projects.gym.infrastructure.datasource.repository;

import java.util.List;
import java.util.UUID;

import com.epam.projects.gym.infrastructure.datasource.entity.TraineeEntity;

/**
 * DAO for Trainee entity.
 * @author lherreram
 *
 */
public interface TraineeRepository {
	
	/**
	 * Retrieves all trainees from the storage.
	 * @return List of trainees from db.
	 */
	public List<TraineeEntity> findAll();
	
	/**
	 * Retrieves a trainee by ID from the storage.
	 * @return Found trainee from db.
	 */
	public TraineeEntity findById(UUID id);
	
	/**
	 * Save a trainee.
	 */
	public void save(TraineeEntity trainee);
	
	
	/**
	 * Delete a Trainee by ID.
	 */
	public void deleteById(UUID id);

	public TraineeEntity findByUserId(UUID userId);
	
}
