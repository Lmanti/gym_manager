package com.epam.projects.gym.infrastructure.adapter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.projects.gym.domain.entity.TrainingType;
import com.epam.projects.gym.domain.repository.TrainingTypeRepository;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainingTypeEntity;
import com.epam.projects.gym.infrastructure.datasource.repository.TrainingTypeJpaRepository;
import com.epam.projects.gym.infrastructure.exception.DatabaseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TrainingTypeAdapter implements TrainingTypeRepository{

	private TrainingTypeJpaRepository trainingTypeJpaRepository;
	
	public TrainingTypeAdapter(TrainingTypeJpaRepository trainingTypeJpaRepository) {
		this.trainingTypeJpaRepository = trainingTypeJpaRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<TrainingType> findByName(String trainingTypeName) {
		try {
			Optional<TrainingTypeEntity> trainingType = trainingTypeJpaRepository
					.findByName(trainingTypeName);
			if (trainingType.isPresent()) {
				return Optional.of(trainingType.get().toDomain());
			} else {
				return Optional.empty();
			}
		} catch (Exception e) {
			log.error("Error while trying to find a training type by name.", e);
			throw new DatabaseException("Error while trying to find a training type by name.", e);
		}
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<TrainingType> getAllTrainingTypes() {
		try {
			log.debug("Trying to fetch all Training types.");
			List<TrainingTypeEntity> foundTrainingTypes = trainingTypeJpaRepository.findAll();
			if (!foundTrainingTypes.isEmpty()) {
				return foundTrainingTypes.stream().map(TrainingTypeEntity::toDomain).collect(Collectors.toList());			
			} else {
				return Collections.emptyList();
			}			
		} catch (Exception e) {
			log.error("Error while trying to fetch all Training types from the database.", e);
			throw new DatabaseException("Error while trying to fetch all Training types from the database.", e);
		}
	}
	
}
