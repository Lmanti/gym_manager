package com.epam.projects.gym.infrastructure.datasource.repository.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.projects.gym.infrastructure.datasource.entity.TrainingEntity;
import com.epam.projects.gym.infrastructure.datasource.repository.StorageRepository;
import com.epam.projects.gym.infrastructure.datasource.repository.TrainingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TrainingRepositoryImpl implements TrainingRepository {
	
	private final String ENTITY_NAME = "Training";

	@Autowired
	private StorageRepository storage;
	
	@Override
	public List<TrainingEntity> findAll() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		Map<UUID, TrainingEntity> trainings = getTrainingsData(storage.getData());
		return trainings
				.entrySet()
				.stream()
				.map(training -> objectMapper.convertValue(training.getValue(), TrainingEntity.class))
				.collect(Collectors.toList());
	}

	@Override
	public TrainingEntity findById(UUID id) {
		try {
			TrainingEntity t = getTrainingsData(storage.getData()).get(id);
			if (t != null) {
				return t;
			} else {
				log.info("There're no trainings with ID: {}", id);
				return null;
			}
		} catch (Exception e) {
			log.error("There're no trainings with ID: {}", e);
			return null;
		}
	}

	@Override
	public void save(TrainingEntity training) {
		try {
			Map<String, Object> db = storage.getData();
			getTrainingsData(db).put(training.getTrainingId(), training);
			storage.save(db);
			log.debug("Training saved successfully.");
		} catch (Exception e) {
			log.error("Error trying to save the training", e);
		}
	}
	
	@Override
	public void deleteById(UUID id) {
		try {
			Map<String, Object> db = storage.getData();
			TrainingEntity t = getTrainingsData(db).get(id);;
			if (t != null) {
				getTrainingsData(db).remove(t.getTrainingId());
				storage.save(db);
				log.debug("Training deleted successfully.");
			} else {
				log.warn("There're no Trainings with id: {}", id);
			}
		} catch (Exception e) {
			log.error("Error trying to delete a Training.", e);
		}
	}

	@SuppressWarnings("unchecked")
	private Map<UUID, TrainingEntity> getTrainingsData(Map<String, Object> db) {
		try {
			Map<UUID, TrainingEntity> trainings = (Map<UUID, TrainingEntity>) db.get(ENTITY_NAME);
			return trainings;
		} catch (Exception e) {
			log.error("Error retrieving trainings data from storage", e);
			return Collections.emptyMap();
		}
	}

}
