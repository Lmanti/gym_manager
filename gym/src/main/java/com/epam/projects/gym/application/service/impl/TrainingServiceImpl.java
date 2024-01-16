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
import com.epam.projects.gym.domain.repository.TraineeRepository;
import com.epam.projects.gym.domain.repository.TrainerRepository;
import com.epam.projects.gym.domain.repository.TrainingRepository;
import com.epam.projects.gym.domain.repository.TrainingTypeRepository;
import com.epam.projects.gym.infrastructure.exception.NotFoundException;

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
		try {
			Optional<Trainee> trainee = traineeRepository.findByUsername(training.getTraineeUsername());
			Optional<Trainer> trainer = trainerRepository.findByUsername(training.getTrainerUsername());
			Optional<TrainingType> trainingType = trainingTypeRepository.findByName(training.getTrainingTypeName().getLabel());
			if (!trainee.isPresent()) {
				throw new NotFoundException("Couldn't find a trainee with username: " + training.getTraineeUsername());
			} else if (!trainer.isPresent()) {
				throw new NotFoundException("Couldn't find a trainer with username: " + training.getTrainerUsername());
			} else if (!trainingType.isPresent()) {
				throw new NotFoundException("Couldn't find a training type with name: " + training.getTrainingTypeName().getLabel());
			} else {
				Training newTraining = new Training(
						trainee.get(),
						trainer.get(),
						training.getTrainingName(),
						trainingType.get(),
						training.getTrainingDate(),
						training.getDuration());
				Training createdTraining = trainingRepository.createTraining(newTraining);
				return createdTraining != null;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
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
