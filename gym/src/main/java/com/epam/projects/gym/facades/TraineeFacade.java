package com.epam.projects.gym.facades;

import java.util.List;

import com.epam.projects.gym.dto.requests.TraineeRegister;
import com.epam.projects.gym.dto.requests.TraineeUpdate;
import com.epam.projects.gym.dto.responses.TraineeProfile;
import com.epam.projects.gym.dto.responses.TraineeUpdated;
import com.epam.projects.gym.dto.responses.UserCreated;

public interface TraineeFacade {
	
	public List<TraineeProfile> getAllTrainees();
	
	public UserCreated createTrainee(TraineeRegister trainee);
	
	public TraineeUpdated updateTrainee(TraineeUpdate trainee);
	
	public boolean deleteTraineeById(String id);

	public TraineeProfile getTraineeByUsername(String username);

	public boolean deleteTraineeByUsername(String username);

}
