package com.epam.projects.gym.application.service;

import java.util.List;

import com.epam.projects.gym.application.dto.requests.TraineeTraining;
import com.epam.projects.gym.application.dto.requests.TrainerTraining;
import com.epam.projects.gym.application.dto.requests.TrainingCreate;
import com.epam.projects.gym.application.dto.responses.Trainings2Trainee;
import com.epam.projects.gym.application.dto.responses.Trainings2Trainers;

public interface TrainingService {

	public boolean createTraining(TrainingCreate training);

	public List<Trainings2Trainee> getTrainingsForTrainee(TraineeTraining training);

	public List<Trainings2Trainers> getTrainingsForTrainer(TrainerTraining training);

}
