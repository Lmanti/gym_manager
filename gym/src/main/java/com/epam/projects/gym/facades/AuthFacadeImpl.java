package com.epam.projects.gym.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.projects.gym.dto.basics.TraineeBasicDto;
import com.epam.projects.gym.dto.basics.TrainerBasicDto;
import com.epam.projects.gym.dto.requests.ChangeLogin;
import com.epam.projects.gym.dto.requests.UserLogin;
import com.epam.projects.gym.services.TraineeService;
import com.epam.projects.gym.services.TrainerService;

@Service
public class AuthFacadeImpl implements AuthFacade {
	
	private TraineeService traineeService;
	
	private TrainerService trainerService;
	
	@Autowired
	public AuthFacadeImpl(TraineeService traineeService, TrainerService trainerService) {
		this.traineeService = traineeService;
		this.trainerService = trainerService;
	}

	@Override
	public boolean login(UserLogin user) {
		boolean access = false;
		TraineeBasicDto trainee = traineeService.getTraineeByUsername(user.getUsername());
		if (trainee != null) {
			access = trainee.getPassword().equals(user.getPassword());
		}
		TrainerBasicDto trainer = trainerService.getTrainerByUsername(user.getUsername());
		if (trainer != null) {
			access = trainer.getPassword().equals(user.getPassword());
		}
		return access;
	}

	@Override
	public boolean changeLogin(ChangeLogin user) {
		boolean done = false;
		TraineeBasicDto trainee = traineeService.getTraineeByUsername(user.getUsername());
		if (trainee != null) {
			done = trainee.getPassword().equals(user.getPassword());
			if (done) {
				traineeService.changeTraineePassword(user.getUsername(), user.getNewPassword());
			}
		}
		TrainerBasicDto trainer = trainerService.getTrainerByUsername(user.getUsername());
		if (trainer != null) {
			done = trainer.getPassword().equals(user.getPassword());
			if (done) {
				trainerService.changeTrainerPassword(user.getUsername(), user.getNewPassword());
			}
		}
		return done;
	}

}
