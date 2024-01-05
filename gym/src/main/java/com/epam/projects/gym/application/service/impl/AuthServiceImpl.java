package com.epam.projects.gym.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.projects.gym.application.dto.basics.TraineeBasicDto;
import com.epam.projects.gym.application.dto.basics.TrainerBasicDto;
import com.epam.projects.gym.application.dto.requests.ChangeLogin;
import com.epam.projects.gym.application.dto.requests.UserLogin;
import com.epam.projects.gym.application.service.AuthService;
import com.epam.projects.gym.infrastructure.adapter.TraineeAdapter;
import com.epam.projects.gym.infrastructure.adapter.TrainerAdapter;

@Service
public class AuthServiceImpl implements AuthService {
	
	private TraineeAdapter traineeService;
	
	private TrainerAdapter trainerService;
	
	@Autowired
	public AuthServiceImpl(TraineeAdapter traineeService, TrainerAdapter trainerService) {
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
