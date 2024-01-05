package com.epam.projects.gym.repositories;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.projects.gym.models.Training;
import com.epam.projects.gym.storage.StorageDao;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation for Training DAO.
 * @author lherreram
 */
@Repository
public class TrainingDaoImpl implements TrainingDao {
	
	private final String ENTITY_NAME = "Training";
	
	/**
	 * Logger for TrainingDaoImpl.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TrainingDaoImpl.class);

	/**
	 * Storage DAO.
	 */
	@Autowired
	private StorageDao storage;
	
	@Override
	public List<Training> findAll() {
		ObjectMapper om = new ObjectMapper();
		Map<UUID, Training> trainings = getTrainingsData(storage.getData());
		return trainings
				.entrySet()
				.stream()
				.map(training -> om.convertValue(training.getValue(), Training.class))
				.collect(Collectors.toList());
	}

	@Override
	public Training findById(UUID id) {
		try {
			Training t = getTrainingsData(storage.getData()).get(id);
			if (t != null) {
				return t;
			} else {
				logger.info("There're no trainings with ID: {}", id);
				return null;
			}
		} catch (Exception e) {
			logger.error("There're no trainings with ID: {}", e);
			return null;
		}
	}

	@Override
	public void save(Training training) {
		try {
			Map<String, Object> db = storage.getData();
			getTrainingsData(db).put(training.getId(), training);
			storage.save(db);
			logger.info("Training saved successfully.");
		} catch (Exception e) {
			logger.error("Error trying to save the training", e);
		}
	}
	
	@Override
	public void deleteById(UUID id) {
		try {
			Map<String, Object> db = storage.getData();
			Training t = getTrainingsData(db).get(id);;
			if (t != null) {
				getTrainingsData(db).remove(t.getId());
				storage.save(db);
				logger.info("Training deleted successfully.");
			} else {
				logger.warn("There're no Trainings with id: {}", id);
			}
		} catch (Exception e) {
			logger.error("Error trying to delete a Training.", e);
		}
	}
	
	/**
	 * Parse a Java Map of trainings to a List of {@link Training}
	 * @param db
	 * 		- Database to search.
	 * @return List of trainings.
	 */
	@SuppressWarnings("unchecked")
	private Map<UUID, Training> getTrainingsData(Map<String, Object> db) {
		try {
			Map<UUID, Training> trainings = (Map<UUID, Training>) db.get(ENTITY_NAME);
			return trainings;
		} catch (Exception e) {
			logger.error("Error retrieving trainings data from storage", e);
			return Collections.emptyMap();
		}
	}

}
