package com.epam.projects.gym.application.service;

import java.util.List;
import java.util.Optional;

import com.epam.projects.gym.application.dto.TrainerAssignedDto;
import com.epam.projects.gym.application.dto.request.ChangeUserStatus;
import com.epam.projects.gym.application.dto.request.TrainerRegister;
import com.epam.projects.gym.application.dto.request.TrainerUpdate;
import com.epam.projects.gym.application.dto.response.TrainerProfile;
import com.epam.projects.gym.application.dto.response.TrainerUpdated;
import com.epam.projects.gym.application.dto.response.UserCreated;

public interface TrainerService {

	public List<TrainerProfile> getAllTrainers();

	public Optional<UserCreated> createTrainer(TrainerRegister trainer);
	
	public Optional<TrainerUpdated> updateTrainer(TrainerUpdate update);

	public Optional<TrainerProfile> getTrainerByUsername(String username);
	
	public boolean changeTrainerPassword(String username, String oldPassword, String newPassword);

	public List<TrainerAssignedDto> getAllNonAssociatedTrainers(String username);

	public boolean changeTrainerStatus(ChangeUserStatus request);

}
