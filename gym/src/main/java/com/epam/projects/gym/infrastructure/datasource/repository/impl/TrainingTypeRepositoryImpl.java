package com.epam.projects.gym.infrastructure.datasource.repository.impl;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.projects.gym.infrastructure.datasource.entity.TrainingTypeEntity;
import com.epam.projects.gym.infrastructure.datasource.repository.StorageRepository;
import com.epam.projects.gym.infrastructure.datasource.repository.TrainingTypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TrainingTypeRepositoryImpl implements TrainingTypeRepository {
	
	private final String ENTITY_NAME = "TrainingType";

	@Autowired
	private StorageRepository storage;

	@Override
	public void save(TrainingTypeEntity trainingType) {
		try {
			Map<String, Object> db = storage.getData();
			getTrainingTypesData(db).put(trainingType.getTrainingTypeId(), trainingType);
			storage.save(db);
			log.debug("Training type saved successfully.");
		} catch (Exception e) {
			log.error("Error trying to save the Training type", e);
		}		
	}

	@Override
	public TrainingTypeEntity findById(UUID id) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			Optional<TrainingTypeEntity> t = getTrainingTypesData(storage.getData())
					.entrySet()
					.stream()
					.map(x -> objectMapper.convertValue(x.getValue(), TrainingTypeEntity.class))
					.filter(x -> x.getTrainingTypeId().equals(id))
					.findAny();
			if (t.isPresent()) {
				return t.get();
			} else {
				log.info("There're no Training types with ID: {}", id);
				return null;
			}
		} catch (Exception e) {
			log.error("There're no Training types with ID: {}", e);
			return null;
		}
	}
	
	@Override
	public TrainingTypeEntity findByName(String specialization) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			Optional<TrainingTypeEntity> t = getTrainingTypesData(storage.getData())
					.entrySet()
					.stream()
					.map(x -> objectMapper.convertValue(x.getValue(), TrainingTypeEntity.class))
					.filter(x -> x.getName().equals(specialization))
					.findAny();
			if (t.isPresent()) {
				return t.get();
			} else {
				log.info("There're no Training types with name: {}", specialization);
				return null;
			}
		} catch (Exception e) {
			log.error("There're no Training types with ID: {}", e);
			return null;
		}
	}

	@Override
	public void deleteById(UUID id) {
		try {
			Map<String, Object> db = storage.getData();
			TrainingTypeEntity t = getTrainingTypesData(db).get(id);;
			if (t != null) {
				getTrainingTypesData(db).remove(t.getTrainingTypeId());
				storage.save(db);
				log.info("Training type deleted successfully.");
			} else {
				log.warn("There're no Training types with id: {}", id);
			}
		} catch (Exception e) {
			log.error("Error trying to delete the Training type.", e);
		}
	}

	@SuppressWarnings("unchecked")
	private Map<UUID, TrainingTypeEntity> getTrainingTypesData(Map<String, Object> db) {
		try {
			Map<UUID, TrainingTypeEntity> trainingTypes = (Map<UUID, TrainingTypeEntity>) db.get(ENTITY_NAME);
			return trainingTypes;
		} catch (Exception e) {
			log.error("Error retrieving training types data from storage", e);
			return Collections.emptyMap();
		}
	}

}
