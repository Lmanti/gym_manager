package com.epam.projects.gym.infrastructure.datasource.repository;

import java.util.List;
import java.util.UUID;

import com.epam.projects.gym.infrastructure.datasource.entity.TrainingEntity;

/**
 * DAO for Training entity.
 * @author lherreram
 */
public interface TrainingRepository {
	
	/**
	 * Retrieves all Training from the storage.
	 * @return List of Training from db.
	 */
	public List<TrainingEntity> findAll();
	
	/**
	 * Retrieves a training by ID from the storage.
	 * @return Found training from db.
	 */
	public TrainingEntity findById(UUID id);
	
	/**
	 * Creates a new training.
	 */
	public void save(TrainingEntity training);
	
	/**
	 * Delete a Training by ID.
	 */
	public void deleteById(UUID id);

}
