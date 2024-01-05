package com.epam.projects.gym.infrastructure.datasource.repository;

import java.util.UUID;

import com.epam.projects.gym.infrastructure.datasource.entity.TrainingTypeEntity;

/**
 * DAO for TrainingType entity.
 * @author lherreram
 */
public interface TrainingTypeRepository {
	
	/**
	 * Save a new Training type.
	 */
	public void save(TrainingTypeEntity trainingType);
	
	/**
	 * Retrieves a Training type by ID from the storage.
	 * @return Found Training type from db.
	 */
	public TrainingTypeEntity findById(UUID id);
	
	/**
	 * Delete an Training type by ID.
	 */
	public void deleteById(UUID id);

	public TrainingTypeEntity findByName(String specialization);

}
