package com.epam.projects.gym.domain.repository;

import java.util.List;
import java.util.Optional;

import com.epam.projects.gym.application.dto.request.TraineeTraining;
import com.epam.projects.gym.domain.entity.Trainee;
import com.epam.projects.gym.domain.entity.Trainer;

public interface TraineeRepository {

	public Optional<Trainee> createTrainee(Trainee newTrainee);
	
	public List<Trainee> getAllTrainees();

	public Optional<Trainee> findByUsername(String username);

	public Optional<Trainee> updateTrainee(Trainee trainee);

	public boolean deleteTrainee(String username);

	public List<Trainer> assignTrainerBulk(TraineeTraining specification, List<String> trainerList);

	public Optional<Trainee> findByUsernameAndPassword(String username, String password);

	public boolean existByUsername(String username);

}
