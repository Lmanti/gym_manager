package com.epam.projects.gym.application.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.projects.gym.application.dto.basics.TraineeBasicDto;
import com.epam.projects.gym.application.dto.basics.TrainerBasicDto;
import com.epam.projects.gym.application.dto.basics.TrainingBasicDto;
import com.epam.projects.gym.application.dto.requests.TraineeTraining;
import com.epam.projects.gym.application.dto.requests.TrainerTraining;
import com.epam.projects.gym.application.dto.requests.TrainingCreate;
import com.epam.projects.gym.application.dto.responses.Trainings2Trainee;
import com.epam.projects.gym.application.dto.responses.Trainings2Trainers;
import com.epam.projects.gym.application.service.TrainingService;
import com.epam.projects.gym.infrastructure.adapter.TraineeAdapter;
import com.epam.projects.gym.infrastructure.adapter.TrainerAdapter;
import com.epam.projects.gym.infrastructure.adapter.TrainingAdapter;

@Service
public class TrainingServiceImpl implements TrainingService {
	
	private TrainingAdapter trainingService;
	
	private TraineeAdapter traineeService;
	
	private TrainerAdapter trainerService;
	
	@Autowired
	public TrainingServiceImpl(
			TrainingAdapter trainingService,
			TraineeAdapter traineeService,
			TrainerAdapter trainerService
			) {
		this.trainingService = trainingService;
		this.traineeService = traineeService;
		this.trainerService = trainerService;
	}

	@Override
	public boolean createTraining(TrainingCreate training) {
		return trainingService.createTraining(training);
	}

	@Override
	public List<Trainings2Trainee> getTrainingsForTrainee(TraineeTraining training) {
		TraineeBasicDto trainee = traineeService.getTraineeByUsername(training.getUsername());
		List<TrainingBasicDto> dtos = trainingService.getAllTraining();
		if (trainee != null && !dtos.isEmpty()) {
			return dtos.stream().filter(x -> {
				TrainerBasicDto trainer = trainerService.getTrainerById(x.getTrainerId());
				boolean matchId = x.getTraineeId().equals(trainee.getTraineeId());
				boolean isAfter = training.getPeriodFrom() != null
						? x.getTrainingDate().isAfter(training.getPeriodFrom())
						: false;
				boolean isBefore = training.getPeriodTo() != null
						? x.getTrainingDate().isBefore(training.getPeriodTo())
						: false;
				boolean matchName = trainer != null && trainer.getFirstName() != null
							&& !trainer.getFirstName().isEmpty()
						? trainer.getFirstName().equals(training.getTrainerName())
						: false;
				return matchId || isAfter || isBefore || matchName;
			}).map(y -> {
					Trainings2Trainee filtrado = new Trainings2Trainee();
					TrainerBasicDto trainer = trainerService.getTrainerById(y.getTrainerId());
					filtrado.setTrainingName(y.getName());
					filtrado.setTrainingDate(y.getTrainingDate());
					filtrado.setDuration(y.getDuration());
					filtrado.setTrainerName(trainer.getFirstName().concat(" ").concat(trainer.getLastName()));
					filtrado.setTrainingType(trainer.getSpecialization());
					return filtrado;
				}).collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}		
	}

	@Override
	public List<Trainings2Trainers> getTrainingsForTrainer(TrainerTraining training) {
		TrainerBasicDto trainer = trainerService.getTrainerByUsername(training.getUsername());
		List<TrainingBasicDto> dtos = trainingService.getAllTraining();
		if (trainer != null && !dtos.isEmpty()) {
			return dtos.stream().filter(x -> {
				TraineeBasicDto trainee = traineeService.getTraineeById(x.getTraineeId());
				boolean matchId = x.getTrainerId().equals(trainer.getTrainerId());
				
				boolean isAfter = training.getPeriodFrom() != null
						? x.getTrainingDate().isAfter(training.getPeriodFrom())
						: false;
				boolean isBefore = training.getPeriodTo() != null
						? x.getTrainingDate().isBefore(training.getPeriodTo())
						: false;
				boolean matchName = trainee != null && trainee.getFirstName() != null
							&& !trainee.getFirstName().isEmpty()
						? trainee.getFirstName().equals(training.getTrainerName())
						: false;
				return matchId || isAfter || isBefore || matchName;
			}).map(y -> {
					Trainings2Trainers filtrado = new Trainings2Trainers();
					TraineeBasicDto trainee = traineeService.getTraineeById(y.getTraineeId());
					filtrado.setTrainingName(y.getName());
					filtrado.setTrainingDate(y.getTrainingDate());
					filtrado.setDuration(y.getDuration());
					filtrado.setTraineeName(trainee.getFirstName().concat(" ").concat(trainee.getLastName()));
					filtrado.setTrainingType(trainer.getSpecialization());
					return filtrado;
				}).collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
	}
	
}
