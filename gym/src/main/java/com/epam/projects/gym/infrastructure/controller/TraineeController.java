package com.epam.projects.gym.infrastructure.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.projects.gym.application.dto.TrainerAssignedDto;
import com.epam.projects.gym.application.dto.request.ChangeUserStatus;
import com.epam.projects.gym.application.dto.request.TraineeRegister;
import com.epam.projects.gym.application.dto.request.TraineeUpdate;
import com.epam.projects.gym.application.dto.request.UpdateTrainerList;
import com.epam.projects.gym.application.dto.response.TraineeProfile;
import com.epam.projects.gym.application.dto.response.TraineeUpdated;
import com.epam.projects.gym.application.dto.response.UserCreated;
import com.epam.projects.gym.application.service.TraineeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/trainees")
@Api(tags = "Trainees")
public class TraineeController {
	
	private TraineeService traineeService;
	
	public TraineeController(TraineeService traineeService) {
		this.traineeService = traineeService;
	}
	
	@GetMapping
	@ApiOperation(value = "Retrieve all trainees.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainees found successfully.")
    })
	public ResponseEntity<List<TraineeProfile>> getAllTrainees() {
		List<TraineeProfile> trainees = traineeService.getAllTrainees();
		return ResponseEntity.status(HttpStatus.OK).body(trainees);
    }
	
	@GetMapping("/{username}")
	@ApiOperation(value = "Retrieve a trainee by username.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainee found successfully."),
            @ApiResponse(code = 404, message = "No trainee can be found.")
    })
	public ResponseEntity<Object> getTraineeByUsername(@PathVariable String username) {
		Optional<TraineeProfile> trainee = traineeService.getTraineeByUsername(username);
		return ResponseEntity.status(HttpStatus.OK).body(trainee.get());
    }

	@PostMapping
	@ApiOperation(value = "Creates a new trainee")
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Trainee registered successfully."),
            @ApiResponse(code = 400, message = "Register failed, please check the info.")
    })
	public ResponseEntity<Object> createTrainee(@RequestBody @Valid TraineeRegister trainee) {
		Optional<UserCreated> newTrainee = traineeService.createTrainee(trainee);
		return ResponseEntity.status(HttpStatus.CREATED).body(newTrainee.get());
    }
	
	@PutMapping
	@ApiOperation(value = "Updates a trainee")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainee updated successfully."),
            @ApiResponse(code = 400, message = "Update failed, please check the info.")
    })
	public ResponseEntity<Object> updateTrainee(@RequestBody @Valid TraineeUpdate trainee) {
		Optional<TraineeUpdated> updated = traineeService.updateTrainee(trainee);
		return ResponseEntity.status(HttpStatus.OK).body(updated.get());
    }
	
	@PatchMapping
	@ApiOperation(value = "Activate/De-Activate a trainee")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainee updated successfully."),
            @ApiResponse(code = 404, message = "Update failed, please check the info.")
    })
	public ResponseEntity<Object> activateDeactivateTrainee(@RequestBody @Valid ChangeUserStatus request) {
		traineeService.changeTraineeStatus(request);
		return ResponseEntity.status(HttpStatus.OK).build();
    }
	
	@PutMapping("/trainerList")
	@ApiOperation(value = "Updates a trainee's trainer list.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainee's list updated successfully.")
    })
	public ResponseEntity<Object> updateTraineeTrainerList(@RequestBody @Valid UpdateTrainerList newData) {
		List<TrainerAssignedDto> updatedData = traineeService.updateTrainerList(newData);
		return ResponseEntity.status(HttpStatus.OK).body(updatedData);
    }
	
	@DeleteMapping("/{username}")
	@ApiOperation(value = "Delete a trainee by username.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainee deleted successfully."),
            @ApiResponse(code = 404, message = "No trainee can be found.")
    })
	public ResponseEntity<Object> deleteTraineeByUsername(@PathVariable String username) {
		traineeService.deleteTrainee(username);
		return ResponseEntity.status(HttpStatus.OK).build();
    }
	
}
