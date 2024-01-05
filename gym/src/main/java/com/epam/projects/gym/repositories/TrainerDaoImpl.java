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

import com.epam.projects.gym.models.Trainer;
import com.epam.projects.gym.storage.StorageDao;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation for Trainer DAO.
 * @author lherreram
 */
@Repository
public class TrainerDaoImpl implements TrainerDao {
	
	private final String ENTITY_NAME = "Trainer";
	
	/**
	 * Logger for StorageImpl.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TrainerDaoImpl.class);
	
	/**
	 * Storage DAO.
	 */
	@Autowired
	private StorageDao storage;

	@Override
	public List<Trainer> findAll() {
		ObjectMapper om = new ObjectMapper();
		Map<UUID, Trainer> trainers = getTrainersData(storage.getData());
		return trainers
				.entrySet()
				.stream()
				.map(trainer -> om.convertValue(trainer.getValue(), Trainer.class))
				.collect(Collectors.toList());
	}

	@Override
	public Trainer findById(UUID id) {
		try {
			Trainer t = getTrainersData(storage.getData()).get(id);
			if (t != null) {
				return t;
			} else {
				logger.info("There're no Trainers with ID: {}", id);
				return null;
			}
		} catch (Exception e) {
			logger.error("Error trying to search a Trainer by ID: {}", e);
			return null;
		}
	}
	
	@Override
	public Trainer findByUserId(UUID userId) {
		try {
			ObjectMapper om = new ObjectMapper();
			Optional<Trainer> t = getTrainersData(storage.getData())
					.entrySet()
					.stream()
					.map(x -> om.convertValue(x.getValue(), Trainer.class))
					.filter(y -> y.getUserId().equals(userId))
					.findAny();
			if (t.isPresent()) {
				return t.get();
			} else {
				logger.info("There're no trainers with userId: {}", userId);
				return null;
			}
		} catch (Exception e) {
			logger.error("Something happened when trying get a trainer by userId: {}", e);
			return null;
		}
	}

	@Override
	public void save(Trainer trainer) {
		try {
			Map<String, Object> db = storage.getData();
			getTrainersData(db).put(trainer.getId(), trainer);
			storage.save(db);
			logger.info("Trainer saved successfully.");
		} catch (Exception e) {
			logger.error("Error trying to save the Trainer", e);
		}		
	}

	@Override
	public void deleteById(UUID id) {
		try {
			Map<String, Object> db = storage.getData();
			Trainer t = getTrainersData(db).get(id);;
			if (t != null) {
				getTrainersData(db).remove(t.getId());
				storage.save(db);
				logger.info("Trainer deleted successfully.");
			} else {
				logger.warn("There're no Trainers with id: {}", id);
			}
		} catch (Exception e) {
			logger.error("Error trying to delete a Trainer.", e);
		}
	}
	
/**
	 * Parse a Java Map of objects to a Map of {@link Trainer}
	 * @param db
	 * 		- Database to search.
	 * @return Map of trainers.
	 */
	@SuppressWarnings("unchecked")
	private Map<UUID, Trainer> getTrainersData(Map<String, Object> db) {
		try {
			Map<UUID, Trainer> trainers = (Map<UUID, Trainer>) db.get(ENTITY_NAME);
			return trainers;
		} catch (Exception e) {
			logger.error("Error retrieving trainers data from storage", e);
			return Collections.emptyMap();
		}
	}

}
