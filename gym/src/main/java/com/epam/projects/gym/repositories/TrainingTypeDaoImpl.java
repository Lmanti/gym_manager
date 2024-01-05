package com.epam.projects.gym.repositories;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.projects.gym.models.TrainingType;
import com.epam.projects.gym.storage.StorageDao;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation for TrainingType DAO.
 * @author lherreram
 */
@Repository
public class TrainingTypeDaoImpl implements TrainingTypeDao {
	
	private final String ENTITY_NAME = "TrainingType";
	
	/**
	 * Logger for TrainingTypeDaoImpl.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TrainingTypeDaoImpl.class);

	/**
	 * Storage DAO.
	 */
	@Autowired
	private StorageDao storage;

	@Override
	public void save(TrainingType trainingType) {
		try {
			Map<String, Object> db = storage.getData();
			getTrainingTypesData(db).put(trainingType.getId(), trainingType);
			storage.save(db);
			logger.info("Training type saved successfully.");
		} catch (Exception e) {
			logger.error("Error trying to save the Training type", e);
		}		
	}

	@Override
	public TrainingType findById(UUID id) {
		try {
			ObjectMapper om = new ObjectMapper();
			Optional<TrainingType> t = getTrainingTypesData(storage.getData())
					.entrySet()
					.stream()
					.map(x -> om.convertValue(x.getValue(), TrainingType.class))
					.filter(x -> x.getId().equals(id))
					.findAny();
			if (t.isPresent()) {
				return t.get();
			} else {
				logger.info("There're no Training types with ID: {}", id);
				return null;
			}
		} catch (Exception e) {
			logger.error("There're no Training types with ID: {}", e);
			return null;
		}
	}
	
	@Override
	public TrainingType findByName(String specialization) {
		try {
			ObjectMapper om = new ObjectMapper();
			Optional<TrainingType> t = getTrainingTypesData(storage.getData())
					.entrySet()
					.stream()
					.map(x -> om.convertValue(x.getValue(), TrainingType.class))
					.filter(x -> x.getName().equals(specialization))
					.findAny();
			if (t.isPresent()) {
				return t.get();
			} else {
				logger.info("There're no Training types with name: {}", specialization);
				return null;
			}
		} catch (Exception e) {
			logger.error("There're no Training types with ID: {}", e);
			return null;
		}
	}

	@Override
	public void deleteById(UUID id) {
		try {
			Map<String, Object> db = storage.getData();
			TrainingType t = getTrainingTypesData(db).get(id);;
			if (t != null) {
				getTrainingTypesData(db).remove(t.getId());
				storage.save(db);
				logger.info("Training type deleted successfully.");
			} else {
				logger.warn("There're no Training types with id: {}", id);
			}
		} catch (Exception e) {
			logger.error("Error trying to delete the Training type.", e);
		}
	}
	
	/**
	 * Parse a Java Map of objects to a List of {@link TrainingType}
	 * @param db
	 * 		- Database to search.
	 * @return Map of training types.
	 */
	@SuppressWarnings("unchecked")
	private Map<UUID, TrainingType> getTrainingTypesData(Map<String, Object> db) {
		try {
			Map<UUID, TrainingType> trainingTypes = (Map<UUID, TrainingType>) db.get(ENTITY_NAME);
			return trainingTypes;
		} catch (Exception e) {
			logger.error("Error retrieving training types data from storage", e);
			return Collections.emptyMap();
		}
	}

}
