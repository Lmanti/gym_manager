package com.epam.projects.gym.application.mapper;

import com.epam.projects.gym.application.dto.response.Trainings2Trainee;
import com.epam.projects.gym.application.dto.response.Trainings2Trainers;
import com.epam.projects.gym.domain.entity.Training;

public class TrainingMapper {
	
	public static Trainings2Trainee toForTrainee(Training training) {
		Trainings2Trainee dto = new Trainings2Trainee();
		dto.setTrainingName(training.getName());
		dto.setTrainingDate(training.getTrainingDate());
		dto.setTrainingType(training.getTrainingTypeId().getName());
		dto.setDuration(training.getDuration());
		dto.setTrainerName(training.getTrainerId().getFirstName() + " " + training.getTrainerId().getLastName());
		return dto;
	}
	
	public static Trainings2Trainers toForTrainers(Training training) {
		Trainings2Trainers dto = new Trainings2Trainers();
		dto.setTrainingName(training.getName());
		dto.setTrainingDate(training.getTrainingDate());
		dto.setTrainingType(training.getTrainingTypeId().getName());
		dto.setDuration(training.getDuration());
		dto.setTraineeName(training.getTraineeId().getFirstName() + " " + training.getTraineeId().getLastName());
		return dto;
	}

}
