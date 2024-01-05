package com.epam.projects.gym.repositories;

import java.util.List;
import java.util.UUID;

import com.epam.projects.gym.models.Trainee;

/**
 * DAO for Trainee entity.
 * @author lherreram
 *
 */
public interface TraineeDao {
	
	/**
	 * Retrieves all trainees from the storage.
	 * @return List of trainees from db.
	 */
	public List<Trainee> findAll();
	
	/**
	 * Retrieves a trainee by ID from the storage.
	 * @return Found trainee from db.
	 */
	public Trainee findById(UUID id);
	
	/**
	 * Save a trainee.
	 */
	public void save(Trainee trainee);
	
	
	/**
	 * Delete a Trainee by ID.
	 */
	public void deleteById(UUID id);

	public Trainee findByUserId(UUID userId);
	
}
