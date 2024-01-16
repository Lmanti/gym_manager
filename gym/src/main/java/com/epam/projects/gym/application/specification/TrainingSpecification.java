package com.epam.projects.gym.application.specification;

import java.time.LocalDate;

import com.epam.projects.gym.application.dto.request.TraineeTraining;
import com.epam.projects.gym.application.dto.request.TrainerTraining;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainingSpecification {
	
	private String traineeUsername;
	
	private String trainerUsername;

	private LocalDate periodFrom;

	private LocalDate periodTo;

	private String trainerName;
	
	private String traineeName;
	
	private String trainingTypeName;
	
	public TrainingSpecification(TraineeTraining training4Trainee) {
		this.traineeUsername = training4Trainee.getUsername();
		this.periodFrom = training4Trainee.getPeriodFrom();
		this.periodTo = training4Trainee.getPeriodTo();
		this.trainerName = training4Trainee.getTrainerName();
		this.trainingTypeName = training4Trainee.getTrainingType();
	}
	
	public TrainingSpecification(TrainerTraining training4Trainer) {
		this.trainerUsername = training4Trainer.getUsername();
		this.periodFrom = training4Trainer.getPeriodFrom();
		this.periodTo = training4Trainer.getPeriodTo();
		this.traineeName = training4Trainer.getTraineeName();
	}

}
