package com.epam.projects.gym.application.service;

import java.util.List;

import com.epam.projects.gym.application.dto.request.TraineeRegister;
import com.epam.projects.gym.application.dto.request.TraineeUpdate;
import com.epam.projects.gym.application.dto.response.TraineeProfile;
import com.epam.projects.gym.application.dto.response.TraineeUpdated;
import com.epam.projects.gym.application.dto.response.UserCreated;

public interface TraineeService {
	
	public List<TraineeProfile> getAllTrainees();
	
	public TraineeProfile getTraineeByUsername(String username);

	public UserCreated createTrainee(TraineeRegister trainee);
	
	public TraineeUpdated updateTrainee(TraineeUpdate update);
	
	public boolean deleteTrainee(String username);

	public boolean changeTraineePassword(String username, String oldPasword, String newPasword);

	public boolean loginTrainee(String username, String pasword);
}
