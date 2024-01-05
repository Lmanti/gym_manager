package com.epam.projects.gym.repositories;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.projects.gym.models.Trainee;
import com.epam.projects.gym.storage.StorageDao;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation for Trainee DAO.
 * @author lherreram
 */
@Repository
public class TraineeDaoImpl implements TraineeDao {
	
	private final String ENTITY_NAME = "Trainee";
	
	/**
	 * Logger for StorageImpl.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TraineeDaoImpl.class);
	
	/**
	 * Storage DAO.
	 */
	@Autowired
	private StorageDao storage;

	@Override
	public List<Trainee> findAll() {
		ObjectMapper om = new ObjectMapper();
		Map<UUID, Trainee> trainees = getTraineesData(storage.getData());
		return trainees
				.entrySet()
				.stream()
				.map(trainee -> om.convertValue(trainee.getValue(), Trainee.class))
				.collect(Collectors.toList());
	}

	@Override
	public void save(Trainee trainee) {
		try {
			Map<String, Object> db = storage.getData();
			getTraineesData(db).put(trainee.getId(), trainee);
			storage.save(db);
			logger.info("Trainee saved successfully.");
		} catch (Exception e) {
			logger.error("Error trying to save the trainee", e);
		}
	}
	
	@Override
	public Trainee findById(UUID id) {
		try {
			Trainee t = getTraineesData(storage.getData()).get(id);
			if (t != null) {
				return t;
			} else {
				logger.info("There're no trainees with ID: {}", id);
				return null;
			}
		} catch (Exception e) {
			logger.error("There're no trainees with ID: {}", e);
			return null;
		}
	}
	
	@Override
	public Trainee findByUserId(UUID userId) {
		try {
			ObjectMapper om = new ObjectMapper();
			Optional<Trainee> t = getTraineesData(storage.getData())
					.entrySet()
					.stream()
					.map(x -> om.convertValue(x.getValue(), Trainee.class))
					.filter(y -> y.getUserId().equals(userId))
					.findAny();
			if (t.isPresent()) {
				return t.get();
			} else {
				logger.info("There're no trainees with userId: {}", userId);
				return null;
			}
		} catch (Exception e) {
			logger.error("Something happened when trying get a trainee by userId: {}", e);
			return null;
		}
	}

	@Override
	public void deleteById(UUID id) {
		try {
			Map<String, Object> db = storage.getData();
			Trainee t = getTraineesData(db).get(id);;
			if (t != null) {
				getTraineesData(db).remove(t.getId());
				storage.save(db);
				logger.info("Trainee deleted successfully.");
			} else {
				logger.warn("There're no trainees with id: {}", id);
			}
		} catch (Exception e) {
			logger.error("Error trying to delete a trainee.", e);
		}
	}
	
	/**
	 * Parse a Java Map of objects to a Map of {@link Trainee}
	 * @param db
	 * 		- Database to search.
	 * @return Map of trainees.
	 */
	@SuppressWarnings("unchecked")
	private Map<UUID, Trainee> getTraineesData(Map<String, Object> db) {
		try {
			Map<UUID, Trainee> trainees = (Map<UUID, Trainee>) db.get(ENTITY_NAME);
			return trainees;
		} catch (Exception e) {
			logger.error("Error retrieving trainees data from storage", e);
			return Collections.emptyMap();
		}
	}

}
