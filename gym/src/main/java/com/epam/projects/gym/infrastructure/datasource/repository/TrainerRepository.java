package com.epam.projects.gym.infrastructure.datasource.repository;

import java.util.List;
import java.util.UUID;

import com.epam.projects.gym.infrastructure.datasource.entity.TrainerEntity;

/**
 * DAO for Trainer entity.
 * @author lherreram
 *
 */
public interface TrainerRepository {
	
/**
	 * Retrieves all Trainers from the storage.
	 * @return List of Trainers from db.
	 */
	public List<TrainerEntity> findAll();
	
	/**
	 * Retrieves a Trainer by ID from the storage.
	 * @return Found Trainer from db.
	 */
	public TrainerEntity findById(UUID id);
	
	/**
	 * Save a Trainer.
	 */
	public void save(TrainerEntity trainer);
	
	
	/**
	 * Delete a Trainer by ID.
	 */
	public void deleteById(UUID id);

	public TrainerEntity findByUserId(UUID userId);

}
