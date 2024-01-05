package com.epam.projects.gym.repositories;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.projects.gym.models.User;
import com.epam.projects.gym.storage.StorageDao;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation for User DAO.
 * @author lherreram
 */
@Repository
public class UserDaoImpl implements UserDao {
	
	private final String ENTITY_NAME = "User";
	
	/**
	 * Logger for UserDaoImpl.
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	/**
	 * Storage DAO.
	 */
	@Autowired
	private StorageDao storage;

	@Override
	public void save(User user) {
		try {
			Map<String, Object> db = storage.getData();
			getUsersData(db).put(user.getId(), user);
			storage.save(db);
			logger.info("User saved successfully.");
		} catch (Exception e) {
			logger.error("Error trying to save the User", e);
		}
	}
	
	@Override
	public User findById(UUID id) {
		try {
			User u = getUsersData(storage.getData()).get(id);
			if (u != null) {
				return u;
			} else {
				logger.info("There're no Users with ID: {}", id);
				return null;
			}
		} catch (Exception e) {
			logger.error("There're no Users with ID: {}", e);
			return null;
		}
	}
	
	@Override
	public User findByUsername(String username) {
		try {
			ObjectMapper om = new ObjectMapper();
			Optional<User> u = getUsersData(storage.getData())
					.entrySet()
					.stream()
					.map(x -> om.convertValue(x.getValue(), User.class))
					.filter(y -> y.getUsername().equals(username))
					.findAny();
			if (u.isPresent()) {
				return u.get();
			} else {
				logger.info("There're no Users with username: {}", username);
				return null;
			}
		} catch (Exception e) {
			logger.error("Something happened when trying to get a user by username: {}", e);
			return null;
		}
	}
	
	@Override
	public void deleteById(UUID id) {
		try {
			Map<String, Object> db = storage.getData();
			User u = getUsersData(db).get(id);;
			if (u != null) {
				getUsersData(db).remove(u.getId());
				storage.save(db);
				logger.info("User deleted successfully.");
			} else {
				logger.warn("There're no Users with id: {}", id);
			}
		} catch (Exception e) {
			logger.error("Error trying to delete the User.", e);
		}
	}
	
	/**
	 * Parse a Java Map of objects to a List of {@link User}
	 * @param db
	 * 		- Database to search.
	 * @return Map of users.
	 */
	@SuppressWarnings("unchecked")
	private Map<UUID, User> getUsersData(Map<String, Object> db) {
		try {
			Map<UUID, User> users = (Map<UUID, User>) db.get(ENTITY_NAME);
			return users;
		} catch (Exception e) {
			logger.error("Error retrieving users data from storage", e);
			return Collections.emptyMap();
		}
	}
	
}
