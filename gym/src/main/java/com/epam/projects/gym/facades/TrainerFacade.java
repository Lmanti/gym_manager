package com.epam.projects.gym.facades;

import java.util.List;

import com.epam.projects.gym.dto.requests.TrainerRegister;
import com.epam.projects.gym.dto.requests.TrainerUpdate;
import com.epam.projects.gym.dto.responses.TrainerProfile;
import com.epam.projects.gym.dto.responses.TrainerUpdated;
import com.epam.projects.gym.dto.responses.UserCreated;

public interface TrainerFacade {

	UserCreated createTrainer(TrainerRegister trainer);

	TrainerProfile getTrainerByUsername(String username);

	List<TrainerProfile> getAllTrainers();

	TrainerUpdated updateTrainer(TrainerUpdate trainer);

}
