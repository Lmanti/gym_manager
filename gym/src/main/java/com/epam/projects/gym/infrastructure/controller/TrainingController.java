package com.epam.projects.gym.infrastructure.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.projects.gym.application.dto.request.TraineeTraining;
import com.epam.projects.gym.application.dto.request.TrainerTraining;
import com.epam.projects.gym.application.dto.request.TrainingCreate;
import com.epam.projects.gym.application.dto.response.Trainings2Trainee;
import com.epam.projects.gym.application.dto.response.Trainings2Trainers;
import com.epam.projects.gym.application.service.TrainingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/training")
@Api(tags = "Trainings")
public class TrainingController {
	
	private TrainingService trainingService;
	
	public TrainingController(TrainingService trainingService) {
		this.trainingService = trainingService;
	}

	@PostMapping
	@ApiOperation(value = "Creates a new training.")
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Training registered successfully."),
            @ApiResponse(code = 400, message = "Training adding operation failed, please check the info.")
    })
	public ResponseEntity<Object> createTraining(@RequestBody @Valid TrainingCreate training) {
		trainingService.createTraining(training);
		return ResponseEntity.status(HttpStatus.CREATED).build();
    }
	
	@GetMapping("/trainees")
	@ApiOperation(value = "Retrieve the trainee training list.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainings list retrieved successfully.")
    })
	public ResponseEntity<List<Trainings2Trainee>> getTraineeTrainingList(@ModelAttribute @Valid TraineeTraining training) {
		List<Trainings2Trainee> trainings = trainingService.getTrainingsForTrainee(training);
		return ResponseEntity.status(HttpStatus.OK).body(trainings);
    }
	
	@GetMapping("/trainers")
	@ApiOperation(value = "Retrieve the trainer training list.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainings list retrieved successfully.")
    })
	public ResponseEntity<List<Trainings2Trainers>> getTrainerTrainingList(@ModelAttribute @Valid TrainerTraining training) {
		List<Trainings2Trainers> trainings = trainingService.getTrainingsForTrainer(training);
		return ResponseEntity.status(HttpStatus.OK).body(trainings);
    }

}
