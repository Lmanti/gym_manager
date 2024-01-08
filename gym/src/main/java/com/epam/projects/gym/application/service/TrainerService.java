package com.epam.projects.gym.application.service;

import java.util.List;

import com.epam.projects.gym.application.dto.request.TrainerRegister;
import com.epam.projects.gym.application.dto.request.TrainerUpdate;
import com.epam.projects.gym.application.dto.response.TrainerProfile;
import com.epam.projects.gym.application.dto.response.TrainerUpdated;
import com.epam.projects.gym.application.dto.response.UserCreated;

public interface TrainerService {

	public List<TrainerProfile> getAllTrainers();

	public UserCreated createTrainer(TrainerRegister trainer);
	
	public TrainerUpdated updateTrainer(TrainerUpdate trainer);

	public TrainerProfile getTrainerByUsername(String username);
	
	public boolean changeTrainerPassword(String username, String oldPasword, String newPasword);

	public boolean loginTrainer(String username, String password);

}
