package com.epam.projects.gym.infrastructure.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.projects.gym.application.dto.TrainerAssignedDto;
import com.epam.projects.gym.application.dto.request.ChangeUserStatus;
import com.epam.projects.gym.application.dto.request.TrainerRegister;
import com.epam.projects.gym.application.dto.request.TrainerUpdate;
import com.epam.projects.gym.application.dto.response.TrainerProfile;
import com.epam.projects.gym.application.dto.response.TrainerUpdated;
import com.epam.projects.gym.application.dto.response.UserCreated;
import com.epam.projects.gym.application.service.TrainerService;
import com.epam.projects.gym.domain.exception.CreationException;
import com.epam.projects.gym.domain.exception.NotFoundException;
import com.epam.projects.gym.domain.exception.UpdateException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/trainers")
@Api(tags = "Trainers")
public class TrainerController {
	
	private TrainerService trainerService;
	
	public TrainerController(TrainerService trainerService) {
		this.trainerService = trainerService;
	}
	
	@GetMapping
	@ApiOperation(value = "Retrieve all trainers.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainers found successfully.")
    })
	public ResponseEntity<List<TrainerProfile>> getAllTrainers() {
		List<TrainerProfile> trainers = trainerService.getAllTrainers();
		return ResponseEntity.status(200).body(trainers);
    }
	
	@PostMapping
	@ApiOperation(value = "Creates a new trainer.")
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Trainer registered successfully."),
            @ApiResponse(code = 400, message = "Register failed, please check the info.")
    })
	public ResponseEntity<Object> createTrainer(@Valid @RequestBody TrainerRegister trainer) {
		try {
			Optional<UserCreated> newTrainer = trainerService.createTrainer(trainer);
			return ResponseEntity.status(HttpStatus.CREATED).body(newTrainer.get());
		} catch (CreationException exception) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
		}
    }
	
	@GetMapping("/{username}")
	@ApiOperation(value = "Retrieve a trainer by username.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainer found successfully."),
            @ApiResponse(code = 404, message = "No trainer can be found.")
    })
	public ResponseEntity<Object> getTrainerByUsername(@PathVariable String username) {
		try {
			Optional<TrainerProfile> trainee = trainerService.getTrainerByUsername(username);
			return ResponseEntity.status(HttpStatus.OK).body(trainee.get());
		} catch (NotFoundException exception) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
		}
    }
	
	@PutMapping
	@ApiOperation(value = "Updates a trainer.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainer updated successfully."),
            @ApiResponse(code = 400, message = "Update failed, please check the info.")
    })
	public ResponseEntity<Object> updateTrainee(@Valid @RequestBody TrainerUpdate trainer) {
		try {
			Optional<TrainerUpdated> updated = trainerService.updateTrainer(trainer);
			return ResponseEntity.status(HttpStatus.OK).body(updated.get());
		} catch (UpdateException exception) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
		}
    }
	
	@PatchMapping
	@ApiOperation(value = "Activate/De-Activate a trainer")
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Trainer updated successfully."),
            @ApiResponse(code = 404, message = "Update failed, please check the info.")
    })
	public ResponseEntity<Object> activateDeactivateTrainee(@Valid @RequestBody ChangeUserStatus request) {
		try {
			trainerService.changeTrainerStatus(request);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (NotFoundException exception) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
		}
    }
	
	@GetMapping("/notAssociated")
	@ApiOperation(value = "Gets not assigned on trainee active trainers.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainers found successfully.")
    })
	public ResponseEntity<List<TrainerAssignedDto>> getAllNonAssociatedTrainers(@RequestParam String username) {
		List<TrainerAssignedDto> trainers = trainerService.getAllNonAssociatedTrainers(username);
		return ResponseEntity.status(200).body(trainers);
    }
	
}
