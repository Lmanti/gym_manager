package com.epam.projects.gym.infrastructure.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		return ResponseEntity.status(200).body(trainees);
    }
	
	@GetMapping("/{username}")
	@ApiOperation(value = "Retrieve a trainee by username.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainee found successfully."),
            @ApiResponse(code = 404, message = "No trainee can be found.")
    })
	public ResponseEntity<TraineeProfile> getTraineeByUsername(@RequestParam String username) {
		Optional<TraineeProfile> trainee = traineeService.getTraineeByUsername(username);
		if (trainee.isPresent()) {
			return ResponseEntity.status(200).body(trainee.get());			
		} else {
			return ResponseEntity.status(404).build();
		}
    }

	@PostMapping
	@ApiOperation(value = "Creates a new trainee")
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Trainee registered successfully."),
            @ApiResponse(code = 400, message = "Register failed, please check the info.")
    })
	public ResponseEntity<UserCreated> createTrainee(@RequestBody TraineeRegister trainee) {
		Optional<UserCreated> newTrainee = traineeService.createTrainee(trainee);
		if (newTrainee.isPresent()) {
			return ResponseEntity.status(201).body(newTrainee.get());
		} else {
			return ResponseEntity.status(400).build();
		}
    }
	
	@PutMapping
	@ApiOperation(value = "Updates a trainee")
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Trainee updated successfully."),
            @ApiResponse(code = 400, message = "Update failed, please check the info.")
    })
	public ResponseEntity<TraineeUpdated> updateTrainee(@RequestBody TraineeUpdate trainee) {
		Optional<TraineeUpdated> updated = traineeService.updateTrainee(trainee);
		if (updated.isPresent()) {
			return ResponseEntity.status(201).body(updated.get());			
		} else {
			return ResponseEntity.status(400).build();
		}
    }
	
	@PatchMapping
	@ApiOperation(value = "Activate/De-Activate a trainee")
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Trainee updated successfully."),
            @ApiResponse(code = 404, message = "Update failed, please check the info.")
    })
	public ResponseEntity<Void> activateDeactivateTrainee(@RequestBody ChangeUserStatus request) {
		boolean isChanged = traineeService.changeTraineeStatus(request);
		if (isChanged) {
			return ResponseEntity.status(200).build();
		} else {
			return ResponseEntity.status(401).build();
		}
    }
	
	@PutMapping("/trainerList")
	@ApiOperation(value = "Updates a trainee's trainer list.")
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Trainee's list updated successfully.")
    })
	public ResponseEntity<List<TrainerAssignedDto>> updateTraineeTrainerList(@RequestBody UpdateTrainerList newData) {
		List<TrainerAssignedDto> updatedData = traineeService.updateTrainerList(newData);
		return ResponseEntity.status(200).body(updatedData);
    }
	
	@DeleteMapping("/{username}")
	@ApiOperation(value = "Delete a trainee by username.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainee deleted successfully."),
            @ApiResponse(code = 404, message = "No trainee can be found.")
    })
	public ResponseEntity<Void> deleteTraineeByUsername(@RequestParam String username) {
		boolean deleted = traineeService.deleteTrainee(username);
		if (deleted) {
			return ResponseEntity.status(200).build();			
		} else {
			return ResponseEntity.status(404).build();
		}
    }
	
}
