package com.epam.projects.gym.repositories;

import java.util.List;
import java.util.UUID;

import com.epam.projects.gym.models.Trainer;

/**
 * DAO for Trainer entity.
 * @author lherreram
 *
 */
public interface TrainerDao {
	
/**
	 * Retrieves all Trainers from the storage.
	 * @return List of Trainers from db.
	 */
	public List<Trainer> findAll();
	
	/**
	 * Retrieves a Trainer by ID from the storage.
	 * @return Found Trainer from db.
	 */
	public Trainer findById(UUID id);
	
	/**
	 * Save a Trainer.
	 */
	public void save(Trainer trainer);
	
	
	/**
	 * Delete a Trainer by ID.
	 */
	public void deleteById(UUID id);

	public Trainer findByUserId(UUID userId);

}
