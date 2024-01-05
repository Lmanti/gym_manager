package com.epam.projects.gym.infrastructure.datasource.repository.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.projects.gym.infrastructure.datasource.entity.TraineeEntity;
import com.epam.projects.gym.infrastructure.datasource.repository.StorageRepository;
import com.epam.projects.gym.infrastructure.datasource.repository.TraineeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TraineeRepositoryImpl implements TraineeRepository {
	
	private final String ENTITY_NAME = "Trainee";
	
	@Autowired
	private StorageRepository storage;

	@Override
	public List<TraineeEntity> findAll() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		Map<UUID, TraineeEntity> trainees = getTraineesData(storage.getData());
		return trainees
				.entrySet()
				.stream()
				.map(trainee -> objectMapper.convertValue(trainee.getValue(), TraineeEntity.class))
				.collect(Collectors.toList());
	}

	@Override
	public void save(TraineeEntity trainee) {
		try {
			Map<String, Object> db = storage.getData();
			getTraineesData(db).put(trainee.getTraineeId(), trainee);
			storage.save(db);
			log.debug("Trainee saved successfully.");
		} catch (Exception e) {
			log.error("Error trying to save the trainee", e);
		}
	}
	
	@Override
	public TraineeEntity findById(UUID id) {
		try {
			TraineeEntity t = getTraineesData(storage.getData()).get(id);
			if (t != null) {
				return t;
			} else {
				log.info("There're no trainees with ID: {}", id);
				return null;
			}
		} catch (Exception e) {
			log.error("There're no trainees with ID: {}", e);
			return null;
		}
	}
	
	@Override
	public TraineeEntity findByUserId(UUID userId) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			Optional<TraineeEntity> t = getTraineesData(storage.getData())
					.entrySet()
					.stream()
					.map(x -> objectMapper.convertValue(x.getValue(), TraineeEntity.class))
					.filter(y -> y.getUserId().getId().equals(userId))
					.findAny();
			if (t.isPresent()) {
				return t.get();
			} else {
				log.info("There're no trainees with userId: {}", userId);
				return null;
			}
		} catch (Exception e) {
			log.error("Something happened when trying get a trainee by userId: {}", e);
			return null;
		}
	}

	@Override
	public void deleteById(UUID id) {
		try {
			Map<String, Object> db = storage.getData();
			TraineeEntity t = getTraineesData(db).get(id);;
			if (t != null) {
				getTraineesData(db).remove(t.getTraineeId());
				storage.save(db);
				log.debug("Trainee deleted successfully.");
			} else {
				log.error("There're no trainees with id: {}", id);
			}
		} catch (Exception e) {
			log.error("Error trying to delete a trainee.", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private Map<UUID, TraineeEntity> getTraineesData(Map<String, Object> db) {
		try {
			Map<UUID, TraineeEntity> trainees = (Map<UUID, TraineeEntity>) db.get(ENTITY_NAME);
			return trainees;
		} catch (Exception e) {
			log.error("Error retrieving trainees data from storage", e);
			return Collections.emptyMap();
		}
	}

}
