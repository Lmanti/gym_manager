package com.epam.projects.gym.application.service;

import java.util.List;

import com.epam.projects.gym.application.dto.requests.TrainerRegister;
import com.epam.projects.gym.application.dto.requests.TrainerUpdate;
import com.epam.projects.gym.application.dto.responses.TrainerProfile;
import com.epam.projects.gym.application.dto.responses.TrainerUpdated;
import com.epam.projects.gym.application.dto.responses.UserCreated;

public interface TrainerService {

	UserCreated createTrainer(TrainerRegister trainer);

	TrainerProfile getTrainerByUsername(String username);

	List<TrainerProfile> getAllTrainers();

	TrainerUpdated updateTrainer(TrainerUpdate trainer);

}
