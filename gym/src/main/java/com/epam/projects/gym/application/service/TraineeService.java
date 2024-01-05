package com.epam.projects.gym.application.service;

import java.util.List;

import com.epam.projects.gym.application.dto.requests.TraineeRegister;
import com.epam.projects.gym.application.dto.requests.TraineeUpdate;
import com.epam.projects.gym.application.dto.responses.TraineeProfile;
import com.epam.projects.gym.application.dto.responses.TraineeUpdated;
import com.epam.projects.gym.application.dto.responses.UserCreated;

public interface TraineeService {
	
	public List<TraineeProfile> getAllTrainees();
	
	public UserCreated createTrainee(TraineeRegister trainee);
	
	public TraineeUpdated updateTrainee(TraineeUpdate trainee);
	
	public boolean deleteTraineeById(String id);

	public TraineeProfile getTraineeByUsername(String username);

	public boolean deleteTraineeByUsername(String username);

}
