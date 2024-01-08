package com.epam.projects.gym.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.projects.gym.application.dto.request.ChangeLogin;
import com.epam.projects.gym.application.dto.request.UserLogin;
import com.epam.projects.gym.application.service.TraineeService;
import com.epam.projects.gym.application.service.TrainerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api/auth")
@Api(tags = "Auth")
public class AuthController {
	
	private TraineeService traineeService;
	
	private TrainerService trainerService;
	
	public AuthController(TraineeService traineeService, TrainerService trainerService) {
		this.traineeService = traineeService;
		this.trainerService = trainerService;
	}

	@PostMapping
	@ApiOperation(value = "Logs in to an user.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "User Logged successfully."),
            @ApiResponse(code = 404, message = "Login failed, invalid credentials.")
    })
	public ResponseEntity<Void> login(@RequestBody UserLogin user) {
		boolean traineeLogin = traineeService.loginTrainee(user.getUsername(), user.getPassword());
		if (traineeLogin) {
			return ResponseEntity.status(200).build();
		} else {
			boolean trainerLogin = trainerService.loginTrainer(user.getUsername(), user.getPassword());
			if (trainerLogin) {
				return ResponseEntity.status(200).build();
			} else {
				return ResponseEntity.status(404).build();
			}
		}
    }
	
	@PatchMapping("/trainee")
	@ApiOperation(value = "Changes a trainee's password.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainee password changed successfully."),
            @ApiResponse(code = 404, message = "Change failed, invalid credentials.")
    })
	public ResponseEntity<Void> changeLoginTrainee(@RequestBody ChangeLogin user) {
		boolean isChanged = traineeService.changeTraineePassword(
				user.getUsername(), user.getPassword(), user.getNewPassword());
		if (isChanged) {
			return ResponseEntity.status(200).build();
		} else {
			return ResponseEntity.status(404).build();
		}
    }
	
	@PatchMapping("/trainer")
	@ApiOperation(value = "Changes a trainer's password.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainer password changed successfully."),
            @ApiResponse(code = 404, message = "Change failed, invalid credentials.")
    })
	public ResponseEntity<Void> changeLoginTrainer(@RequestBody ChangeLogin user) {
		boolean isChanged = trainerService.changeTrainerPassword(
				user.getUsername(), user.getPassword(), user.getNewPassword());
		if (isChanged) {
			return ResponseEntity.status(200).build();
		} else {
			return ResponseEntity.status(404).build();
		}
    }

}
