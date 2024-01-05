package com.epam.projects.gym.infrastructure.datasource.repository.impl;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.projects.gym.infrastructure.datasource.entity.UserEntity;
import com.epam.projects.gym.infrastructure.datasource.repository.StorageRepository;
import com.epam.projects.gym.infrastructure.datasource.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {
	
	private final String ENTITY_NAME = "User";

	@Autowired
	private StorageRepository storage;

	@Override
	public void save(UserEntity user) {
		try {
			Map<String, Object> db = storage.getData();
			getUsersData(db).put(user.getId(), user);
			storage.save(db);
			log.debug("User saved successfully.");
		} catch (Exception e) {
			log.error("Error trying to save the User", e);
		}
	}
	
	@Override
	public UserEntity findById(UUID id) {
		try {
			UserEntity u = getUsersData(storage.getData()).get(id);
			if (u != null) {
				return u;
			} else {
				log.info("There're no Users with ID: {}", id);
				return null;
			}
		} catch (Exception e) {
			log.error("There're no Users with ID: {}", e);
			return null;
		}
	}
	
	@Override
	public UserEntity findByUsername(String username) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			Optional<UserEntity> u = getUsersData(storage.getData())
					.entrySet()
					.stream()
					.map(x -> objectMapper.convertValue(x.getValue(), UserEntity.class))
					.filter(y -> y.getUsername().equals(username))
					.findAny();
			if (u.isPresent()) {
				return u.get();
			} else {
				log.info("There're no Users with username: {}", username);
				return null;
			}
		} catch (Exception e) {
			log.error("Something happened when trying to get a user by username: {}", e);
			return null;
		}
	}
	
	@Override
	public void deleteById(UUID id) {
		try {
			Map<String, Object> db = storage.getData();
			UserEntity u = getUsersData(db).get(id);;
			if (u != null) {
				getUsersData(db).remove(u.getId());
				storage.save(db);
				log.debug("User deleted successfully.");
			} else {
				log.warn("There're no Users with id: {}", id);
			}
		} catch (Exception e) {
			log.error("Error trying to delete the User.", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private Map<UUID, UserEntity> getUsersData(Map<String, Object> db) {
		try {
			Map<UUID, UserEntity> users = (Map<UUID, UserEntity>) db.get(ENTITY_NAME);
			return users;
		} catch (Exception e) {
			log.error("Error retrieving users data from storage", e);
			return Collections.emptyMap();
		}
	}
	
}
