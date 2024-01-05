package com.epam.projects.gym.repositories;

import java.util.UUID;

import com.epam.projects.gym.models.User;

/**
 * DAO for User entity.
 * @author lherreram
 */
public interface UserDao {
	
	/**
	 * Save a new user.
	 */
	public void save(User user);
	
	/**
	 * Retrieves a user by ID from the storage.
	 * @return Found user from db.
	 */
	public User findById(UUID id);
	
	/**
	 * Delete an User by ID.
	 */
	public void deleteById(UUID id);

	public User findByUsername(String username);

}
