package com.epam.projects.gym.application.service;

import java.util.List;
import java.util.UUID;

import com.epam.projects.gym.application.dto.basic.TraineeBasicDto;
import com.epam.projects.gym.application.dto.request.TraineeRegister;
import com.epam.projects.gym.application.dto.request.TraineeUpdate;
import com.epam.projects.gym.application.dto.response.TraineeProfile;
import com.epam.projects.gym.application.dto.response.UserCreated;

public interface TraineeService {
	
	public List<TraineeProfile> getAllTrainees();
	
	public TraineeBasicDto getTraineeById(UUID id);
	
	public TraineeProfile getTraineeByUsername(String username);

	public UserCreated createTrainee(TraineeRegister trainee);
	
	public TraineeBasicDto updateTrainee(TraineeUpdate update);
	
	public boolean deleteTrainee(UUID id);

	public List<TraineeBasicDto> getAllByIds(List<UUID> trainees);
	
	public boolean changeTraineePassword(String username, String newPasword);
}
