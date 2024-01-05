package com.epam.projects.gym.infrastructure.datasource.repository;

import java.util.Map;

/**
 * Dao for in-memory storage.
 * @author lherreram
 *
 */
public interface StorageRepository {
	
	/**
	 * Retrieve all data from the JSON file.
	 * @return {@link Map} with database data.
	 */
	public Map<String, Object> getData();
	
	/**
	 * Save data into the JSON file.
	 * @param newData
	 * 		- {@link Map} with the new data to save.
	 */
	public void save(Map<String, Object> newData);
}
