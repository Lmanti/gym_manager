package com.epam.projects.gym.infrastructure.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/trainers")
@Api(tags = "Trainers")
public class TrainerController {
	
//	@Autowired
//	private TrainerFacade facade;
//	
//	@PostMapping
//	@ApiOperation(value = "Creates a new trainer.")
//	@ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Trainer registered successfully."),
//            @ApiResponse(code = 400, message = "Register failed, please check the info.")
//    })
//	public ResponseEntity<UserCreated> createTrainer(@RequestBody TrainerRegister trainer) {
//		UserCreated newTrainer = facade.createTrainer(trainer);
//		if (newTrainer != null) {
//			return ResponseEntity.status(201).body(newTrainer);			
//		} else {
//			return ResponseEntity.status(400).build();
//		}
//    }
//	
//	@GetMapping("/{username}")
//	@ApiOperation(value = "Retrieve a trainer by username.")
//	@ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Trainer found successfully."),
//            @ApiResponse(code = 404, message = "No trainer can be found.")
//    })
//	public ResponseEntity<TrainerProfile> getTrainerByUsername(@RequestParam String username) {
//		TrainerProfile trainer = facade.getTrainerByUsername(username);
//		if (trainer != null) {
//			return ResponseEntity.status(200).body(trainer);			
//		} else {
//			return ResponseEntity.status(404).build();
//		}
//    }
//	
//	@GetMapping
//	@ApiOperation(value = "Retrieve all trainers.")
//	@ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Trainers found successfully.")
//    })
//	public ResponseEntity<List<TrainerProfile>> getAllTrainers() {
//		List<TrainerProfile> trainers = facade.getAllTrainers();
//		return ResponseEntity.status(200).body(trainers);
//    }
//	
//	@PutMapping
//	@ApiOperation(value = "Updates a trainer.")
//	@ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Trainer updated successfully."),
//            @ApiResponse(code = 400, message = "Update failed, please check the info.")
//    })
//	public ResponseEntity<TrainerUpdated> updateTrainee(@RequestBody TrainerUpdate trainee) {
//		TrainerUpdated updated = facade.updateTrainer(trainee);
//		if (updated != null) {
//			return ResponseEntity.status(201).body(updated);	
//		} else {
//			return ResponseEntity.status(400).build();
//		}
//    }
	
}
