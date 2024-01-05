package com.epam.projects.gym.infrastructure.datasource.repository;

import java.util.UUID;

import com.epam.projects.gym.infrastructure.datasource.entity.UserEntity;

/**
 * DAO for User entity.
 * @author lherreram
 */
public interface UserRepository {
	
	/**
	 * Save a new user.
	 */
	public void save(UserEntity user);
	
	/**
	 * Retrieves a user by ID from the storage.
	 * @return Found user from db.
	 */
	public UserEntity findById(UUID id);
	
	/**
	 * Delete an User by ID.
	 */
	public void deleteById(UUID id);

	public UserEntity findByUsername(String username);

}
