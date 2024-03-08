package com.epam.projects.gym.infrastructure.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.projects.gym.application.dto.response.TrainingTypeResponse;
import com.epam.projects.gym.application.service.TrainingTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api/trainingTypes")
@Api(tags = "Training Types")
public class TrainingTypeController {
	
	private TrainingTypeService trainingTypeService;
	
	public TrainingTypeController(TrainingTypeService trainingTypeService) {
		this.trainingTypeService = trainingTypeService;
	}
	
	@GetMapping
	@ApiOperation(value = "Retrieve all training types.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Training types found successfully.")
    })
	public ResponseEntity<Object> getAllTrainees() {
		List<TrainingTypeResponse> trainingTypes = trainingTypeService.getAllTrainingTypes();
		return ResponseEntity.status(HttpStatus.OK).body(trainingTypes);
    }

}
