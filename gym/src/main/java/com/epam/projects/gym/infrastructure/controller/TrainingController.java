package com.epam.projects.gym.infrastructure.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/training")
@Api(tags = "Trainings")
public class TrainingController {
	
//	@Autowired
//	private TrainingFacade facade;
//	
//	@PostMapping
//	@ApiOperation(value = "Creates a new training.")
//	@ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Training registered successfully."),
//            @ApiResponse(code = 400, message = "Training adding operation failed, please check the info.")
//    })
//	public ResponseEntity<Void> createTraining(@RequestBody TrainingCreate training) {
//		boolean newTraining = facade.createTraining(training);
//		if (newTraining) {
//			return ResponseEntity.status(201).build();
//		} else {
//			return ResponseEntity.status(400).build();
//		}
//    }
//	
//	@PostMapping("/trainees")
//	@ApiOperation(value = "Retrieve the trainee training list.")
//	@ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Trainings list retrieved successfully."),
//            @ApiResponse(code = 404, message = "Couldn't find any training with these specifications.")
//    })
//	public ResponseEntity<List<Trainings2Trainee>> getTraineeTrainingList(@RequestBody TraineeTraining training) {
//		List<Trainings2Trainee> trainings = facade.getTrainingsForTrainee(training);
//		if (!trainings.isEmpty()) {
//			return ResponseEntity.status(200).body(trainings);
//		} else {
//			return ResponseEntity.status(404).body(trainings);
//		}
//    }
//	
//	@PostMapping("/trainers")
//	@ApiOperation(value = "Retrieve the trainer training list.")
//	@ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Trainings list retrieved successfully."),
//            @ApiResponse(code = 404, message = "Couldn't find any training with these specifications.")
//    })
//	public ResponseEntity<List<Trainings2Trainers>> getTrainerTrainingList(@RequestBody TrainerTraining training) {
//		List<Trainings2Trainers> trainings = facade.getTrainingsForTrainer(training);
//		if (!trainings.isEmpty()) {
//			return ResponseEntity.status(200).body(trainings);
//		} else {
//			return ResponseEntity.status(404).body(trainings);
//		}
//    }

}
