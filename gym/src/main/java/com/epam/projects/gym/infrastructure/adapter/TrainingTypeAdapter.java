package com.epam.projects.gym.infrastructure.adapter;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.epam.projects.gym.domain.entity.TrainingType;
import com.epam.projects.gym.domain.repository.TrainingTypeRepository;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainingTypeEntity;
import com.epam.projects.gym.infrastructure.datasource.postgresql.repository.TrainingTypeJpaRepository;
import com.epam.projects.gym.infrastructure.exception.DatabaseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TrainingTypeAdapter implements TrainingTypeRepository{

	private TrainingTypeJpaRepository trainingTypeJpaRepository;
	
	public TrainingTypeAdapter(TrainingTypeJpaRepository trainingTypeJpaRepository) {
		this.trainingTypeJpaRepository = trainingTypeJpaRepository;
	}

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
	
}
