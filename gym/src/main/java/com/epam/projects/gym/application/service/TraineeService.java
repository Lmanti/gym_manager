package com.epam.projects.gym.application.service;

import java.util.List;
import java.util.Optional;

import com.epam.projects.gym.application.dto.TrainerAssignedDto;
import com.epam.projects.gym.application.dto.request.ChangeUserStatus;
import com.epam.projects.gym.application.dto.request.TraineeRegister;
import com.epam.projects.gym.application.dto.request.TraineeUpdate;
import com.epam.projects.gym.application.dto.request.UpdateTrainerList;
import com.epam.projects.gym.application.dto.response.TraineeProfile;
import com.epam.projects.gym.application.dto.response.TraineeUpdated;
import com.epam.projects.gym.application.dto.response.UserCreated;

public interface TraineeService {
	
	public List<TraineeProfile> getAllTrainees();
	
	public Optional<TraineeProfile> getTraineeByUsername(String username);

	public Optional<UserCreated> createTrainee(TraineeRegister trainee);
	
	public Optional<TraineeUpdated> updateTrainee(TraineeUpdate update);
	
	public boolean deleteTrainee(String username);

	public boolean changeTraineePassword(String username, String oldPassword, String newPassword);

	public boolean loginTrainee(String username, String password);

	public List<TrainerAssignedDto> updateTrainerList(UpdateTrainerList newData);

	public boolean changeTraineeStatus(ChangeUserStatus request);
}
