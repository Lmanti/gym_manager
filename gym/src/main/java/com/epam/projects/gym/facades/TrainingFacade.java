package com.epam.projects.gym.facades;

import java.util.List;

import com.epam.projects.gym.dto.requests.TraineeTraining;
import com.epam.projects.gym.dto.requests.TrainerTraining;
import com.epam.projects.gym.dto.requests.TrainingCreate;
import com.epam.projects.gym.dto.responses.Trainings2Trainee;
import com.epam.projects.gym.dto.responses.Trainings2Trainers;

public interface TrainingFacade {

	public boolean createTraining(TrainingCreate training);

	public List<Trainings2Trainee> getTrainingsForTrainee(TraineeTraining training);

	public List<Trainings2Trainers> getTrainingsForTrainer(TrainerTraining training);

}
