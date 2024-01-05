package com.epam.projects.gym.infrastructure.datasource.repository.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.projects.gym.infrastructure.datasource.entity.TrainerEntity;
import com.epam.projects.gym.infrastructure.datasource.repository.StorageRepository;
import com.epam.projects.gym.infrastructure.datasource.repository.TrainerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TrainerRepositoryImpl implements TrainerRepository {
	
	private final String ENTITY_NAME = "Trainer";
	
	@Autowired
	private StorageRepository storage;

	@Override
	public List<TrainerEntity> findAll() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		Map<UUID, TrainerEntity> trainers = getTrainersData(storage.getData());
		return trainers
				.entrySet()
				.stream()
				.map(trainer -> objectMapper.convertValue(trainer.getValue(), TrainerEntity.class))
				.collect(Collectors.toList());
	}

	@Override
	public TrainerEntity findById(UUID id) {
		try {
			TrainerEntity t = getTrainersData(storage.getData()).get(id);
			if (t != null) {
				return t;
			} else {
				log.info("There're no Trainers with ID: {}", id);
				return null;
			}
		} catch (Exception e) {
			log.error("Error trying to search a Trainer by ID: {}", e);
			return null;
		}
	}
	
	@Override
	public TrainerEntity findByUserId(UUID userId) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			Optional<TrainerEntity> t = getTrainersData(storage.getData())
					.entrySet()
					.stream()
					.map(x -> objectMapper.convertValue(x.getValue(), TrainerEntity.class))
					.filter(y -> y.getUserId().getId().equals(userId))
					.findAny();
			if (t.isPresent()) {
				return t.get();
			} else {
				log.info("There're no trainers with userId: {}", userId);
				return null;
			}
		} catch (Exception e) {
			log.error("Something happened when trying get a trainer by userId: {}", e);
			return null;
		}
	}

	@Override
	public void save(TrainerEntity trainer) {
		try {
			Map<String, Object> db = storage.getData();
			getTrainersData(db).put(trainer.getTrainerId(), trainer);
			storage.save(db);
			log.debug("Trainer saved successfully.");
		} catch (Exception e) {
			log.error("Error trying to save the Trainer", e);
		}		
	}

	@Override
	public void deleteById(UUID id) {
		try {
			Map<String, Object> db = storage.getData();
			TrainerEntity t = getTrainersData(db).get(id);;
			if (t != null) {
				getTrainersData(db).remove(t.getTrainerId());
				storage.save(db);
				log.debug("Trainer deleted successfully.");
			} else {
				log.info("There're no Trainers with id: {}", id);
			}
		} catch (Exception e) {
			log.error("Error trying to delete a Trainer.", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private Map<UUID, TrainerEntity> getTrainersData(Map<String, Object> db) {
		try {
			Map<UUID, TrainerEntity> trainers = (Map<UUID, TrainerEntity>) db.get(ENTITY_NAME);
			return trainers;
		} catch (Exception e) {
			log.error("Error retrieving trainers data from storage", e);
			return Collections.emptyMap();
		}
	}

}
