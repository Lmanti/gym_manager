package com.epam.projects.gym.application.service;

import java.util.List;

import com.epam.projects.gym.application.dto.request.TraineeTraining;
import com.epam.projects.gym.application.dto.request.TrainerTraining;
import com.epam.projects.gym.application.dto.request.TrainingCreate;
import com.epam.projects.gym.application.dto.response.Trainings2Trainee;
import com.epam.projects.gym.application.dto.response.Trainings2Trainers;

public interface TrainingService {

	public boolean createTraining(TrainingCreate training);

	public List<Trainings2Trainee> getTrainingsForTrainee(TraineeTraining params);

	public List<Trainings2Trainers> getTrainingsForTrainer(TrainerTraining params);

}
