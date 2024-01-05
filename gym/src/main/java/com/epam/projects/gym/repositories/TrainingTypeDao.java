package com.epam.projects.gym.repositories;

import java.util.UUID;

import com.epam.projects.gym.models.TrainingType;

/**
 * DAO for TrainingType entity.
 * @author lherreram
 */
public interface TrainingTypeDao {
	
	/**
	 * Save a new Training type.
	 */
	public void save(TrainingType trainingType);
	
	/**
	 * Retrieves a Training type by ID from the storage.
	 * @return Found Training type from db.
	 */
	public TrainingType findById(UUID id);
	
	/**
	 * Delete an Training type by ID.
	 */
	public void deleteById(UUID id);

	public TrainingType findByName(String specialization);

}
