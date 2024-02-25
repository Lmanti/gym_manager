package com.epam.projects.gym.application.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.epam.projects.gym.application.dto.request.TraineeTraining;
import com.epam.projects.gym.application.dto.request.TrainerTraining;
import com.epam.projects.gym.application.dto.request.TrainingCreate;
import com.epam.projects.gym.application.dto.response.Trainings2Trainee;
import com.epam.projects.gym.application.dto.response.Trainings2Trainers;
import com.epam.projects.gym.application.mapper.TrainingMapper;
import com.epam.projects.gym.application.service.TrainingService;
import com.epam.projects.gym.application.specification.TrainingSpecification;
import com.epam.projects.gym.domain.entity.Trainee;
import com.epam.projects.gym.domain.entity.Trainer;
import com.epam.projects.gym.domain.entity.Training;
import com.epam.projects.gym.domain.entity.TrainingType;
import com.epam.projects.gym.domain.exception.CreationException;
import com.epam.projects.gym.domain.exception.NotFoundException;
import com.epam.projects.gym.domain.repository.TraineeRepository;
import com.epam.projects.gym.domain.repository.TrainerRepository;
import com.epam.projects.gym.domain.repository.TrainingRepository;
import com.epam.projects.gym.domain.repository.TrainingTypeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TrainingServiceImpl implements TrainingService {
	
	private TrainingRepository trainingRepository;
	
	private TraineeRepository traineeRepository;
	
	private TrainerRepository trainerRepository;
	
	private TrainingTypeRepository trainingTypeRepository;
	
	public TrainingServiceImpl(
			TrainingRepository trainingRepository,
			TraineeRepository traineeRepository,
			TrainerRepository trainerRepository,
			TrainingTypeRepository trainingTypeRepository
			) {
		this.trainingRepository = trainingRepository;
		this.traineeRepository = traineeRepository;
		this.trainerRepository = trainerRepository;
		this.trainingTypeRepository = trainingTypeRepository;
	}

	@Override
	public boolean createTraining(TrainingCreate training) {
		log.info("Attempting to create a new Training.");
		log.info("Looking for trainee with username: {}", training.getTraineeUsername());
		Optional<Trainee> trainee = traineeRepository.findByUsername(training.getTraineeUsername());
		log.info("Looking for trainer with username: {}", training.getTrainerUsername());
		Optional<Trainer> trainer = trainerRepository.findByUsername(training.getTrainerUsername());
		log.info("Looking for training type with name: {}", training.getTrainingTypeName().getLabel());
		Optional<TrainingType> trainingType = trainingTypeRepository.findByName(training.getTrainingTypeName().getLabel());
		if (!trainee.isPresent()) {
			throw new NotFoundException("Couldn't find a trainee with username: " + training.getTraineeUsername());
		} else if (!trainer.isPresent()) {
			throw new NotFoundException("Couldn't find a trainer with username: " + training.getTrainerUsername());
		} else if (!trainingType.isPresent()) {
			throw new NotFoundException("Couldn't find a training type with name: " + training.getTrainingTypeName().getLabel());
		} else {
			log.info("Creating a new Training: {}", training);
			Training newTraining = new Training(
					trainee.get(),
					trainer.get(),
					training.getTrainingName(),
					trainingType.get(),
					training.getTrainingDate(),
					training.getDuration());
			Optional<Training> createdTraining = trainingRepository.createTraining(newTraining);
			if (createdTraining.isPresent()) {
				log.info("Training created successfully with ID: {}", createdTraining.get().getId());
				return true;
			} else {
				log.error("Error while trying to create a new Training.");
				throw new CreationException("Error while trying to create a new Training.");
			}
		}
	}

	@Override
	public List<Trainings2Trainee> getTrainingsForTrainee(TraineeTraining params) {
		List<Trainings2Trainee> traineeTrainingList =
				trainingRepository.findBySpecification(new TrainingSpecification(params))
					.stream()
					.map(TrainingMapper::toForTrainee)
					.collect(Collectors.toList());
		return traineeTrainingList;
	}

	@Override
	public List<Trainings2Trainers> getTrainingsForTrainer(TrainerTraining params) {
		List<Trainings2Trainers> trainerTrainingList =
				trainingRepository.findBySpecification(new TrainingSpecification(params))
					.stream()
					.map(TrainingMapper::toForTrainers)
					.collect(Collectors.toList());
		return trainerTrainingList;
	}

}
