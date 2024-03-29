package com.epam.projects.gym.infrastructure.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.projects.gym.application.specification.TrainingSpecification;
import com.epam.projects.gym.domain.entity.Training;
import com.epam.projects.gym.domain.repository.TrainingRepository;
import com.epam.projects.gym.infrastructure.datasource.entity.TraineeEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainerEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainingEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainingTypeEntity;
import com.epam.projects.gym.infrastructure.datasource.repository.TraineeJpaRepository;
import com.epam.projects.gym.infrastructure.datasource.repository.TrainerJpaRepository;
import com.epam.projects.gym.infrastructure.datasource.repository.TrainingJpaRepository;
import com.epam.projects.gym.infrastructure.datasource.repository.TrainingTypeJpaRepository;
import com.epam.projects.gym.infrastructure.exception.DatabaseException;
import com.epam.projects.gym.infrastructure.specification.TrainingEntitySpecification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TrainingAdapter implements TrainingRepository {
	
	private TrainingJpaRepository trainingJpaRepository;
	
	private TraineeJpaRepository traineeJpaRepository;
	
	private TrainerJpaRepository trainerJpaRepository;
	
	private TrainingTypeJpaRepository trainingTypeJpaRepository;
	
	public TrainingAdapter(
			TrainingJpaRepository trainingJpaRepository,
			TraineeJpaRepository traineeJpaRepository,
			TrainerJpaRepository trainerJpaRepository,
			TrainingTypeJpaRepository trainingTypeJpaRepository
			) {
		this.trainingJpaRepository = trainingJpaRepository;
		this.traineeJpaRepository = traineeJpaRepository;
		this.trainerJpaRepository = trainerJpaRepository;
		this.trainingTypeJpaRepository = trainingTypeJpaRepository;
	}

	@Transactional(rollbackFor = DatabaseException.class)
	@Override
	public Optional<Training> createTraining(Training newTraining) {
		log.debug("Creating training: {}", newTraining);
		try {
			Optional<TraineeEntity> trainee = traineeJpaRepository.findById(newTraining.getTraineeId().getId());
			Optional<TrainerEntity> trainer = trainerJpaRepository.findById(newTraining.getTrainerId().getId());
			Optional<TrainingTypeEntity> trainingType = trainingTypeJpaRepository.findById(newTraining.getTrainingTypeId().getId());
			
			TrainingEntity training = new TrainingEntity(
					trainee.get(),
					trainer.get(),
					newTraining.getName(),
					trainingType.get(),
					newTraining.getTrainingDate(),
					newTraining.getDuration()
					);
			
			TrainingEntity createdTraining = trainingJpaRepository.save(training);
			log.debug("Training created successfully with ID: {}", createdTraining.getTrainingId());
			return Optional.of(createdTraining.toDomain());
		} catch (Exception exception) {
			log.error("Error while trying to register a Training.", exception);
			throw new DatabaseException("Error while trying to register a Training.", exception);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<Training> findBySpecification(TrainingSpecification specification) {
		try {
			List<TrainingEntity> trainingList = trainingJpaRepository.findAll(new TrainingEntitySpecification(specification));
			return trainingList.stream().map(TrainingEntity::toDomain).collect(Collectors.toList());			
		} catch (Exception exception) {
			log.error("Error while trying to retrieve specified Trainings data.", exception);
			throw new DatabaseException("Error while trying to retrieve specified Trainings data.", exception);
		}
	}

}
