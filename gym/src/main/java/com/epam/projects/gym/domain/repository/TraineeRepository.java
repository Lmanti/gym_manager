package com.epam.projects.gym.domain.repository;

import java.util.List;

import com.epam.projects.gym.domain.entity.Trainee;

public interface TraineeRepository {

	public Trainee createTrainee(Trainee newTrainee);
	
	public List<Trainee> getAllTrainees();

	public Trainee findByUsername(String username);

	public Trainee updateTrainee(Trainee trainee);

	public boolean deleteTrainee(String username);

}
