package com.epam.projects.gym.repositories;

import java.util.List;
import java.util.UUID;

import com.epam.projects.gym.models.Training;

/**
 * DAO for Training entity.
 * @author lherreram
 */
public interface TrainingDao {
	
	/**
	 * Retrieves all Training from the storage.
	 * @return List of Training from db.
	 */
	public List<Training> findAll();
	
	/**
	 * Retrieves a training by ID from the storage.
	 * @return Found training from db.
	 */
	public Training findById(UUID id);
	
	/**
	 * Creates a new training.
	 */
	public void save(Training training);
	
	/**
	 * Delete a Training by ID.
	 */
	public void deleteById(UUID id);

}
