package com.epam.projects.gym.mappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.epam.projects.gym.dto.basics.TrainingBasicDto;
import com.epam.projects.gym.dto.requests.TrainingCreate;
import com.epam.projects.gym.models.Training;

@Component
public class TrainingMapperImpl implements TrainingMapper {

	/**
	 * Logger for TrainingMapperImpl.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TrainingMapperImpl.class);

	public List<TrainingBasicDto> parseTrainings(List<Training> trainings) {
		try {
			List<TrainingBasicDto> dtoList = new ArrayList<>();
			trainings.stream().forEach(t -> {
				TrainingBasicDto dto = mapIdToTraining(t);
				dtoList.add(dto);
			});
			return dtoList;
		} catch (Exception e) {
			logger.error("Error parsing the trainings list to DTO.", e);
			return Collections.emptyList();
		}
	}
	
	public TrainingBasicDto mapIdToTraining(Training training) {
		TrainingBasicDto dto = new TrainingBasicDto();
		dto.setId(training.getId());
		dto.setTraineeId(training.getTraineeId()); // only for trainer profile
		dto.setTrainerId(training.getTrainerId()); // only for trainee profile
		dto.setTrainingTypeId(training.getTrainingTypeId());
		dto.setName(training.getName());
		dto.setTrainingDate(training.getTrainingDate());
		dto.setDuration(training.getDuration());
		return dto;
	}

	@Override
	public Training mapDtoToTraining(TrainingBasicDto training) {
		if (validateTraining(training)) {
			Training t = new Training(
					training.getTraineeId(),
					training.getTrainerId(),
					training.getName(),
					training.getTrainingTypeId(),
					training.getTrainingDate(),
					training.getDuration());
			return t;
		} else {
			logger.error("Training is not valid, please check the info.");
			return null;
		}
	}
	
	private boolean validateTraining(TrainingBasicDto dto) {
		boolean valid = true;
		if (dto.getName() == null && dto.getName().isEmpty()) {
			logger.warn("Training name cannot be empty.");
			valid = false;
		}
		if (dto.getTrainingDate() == null) {
			logger.warn("Training date cannot be empty.");
			valid = false;
		}
		if (dto.getDuration() == 0) {
			logger.warn("Training duration cannot be empty.");
			valid = false;
		}
		return valid;
	}
	
	@Override
	public boolean validateCreate(TrainingCreate training) {
		boolean valid = true;
		if (training.getTrainingName() == null && training.getTrainingName().isEmpty()) {
			logger.warn("Training name cannot be empty.");
			valid = false;
		}
		if (training.getTrainingDate() == null) {
			logger.warn("Training date cannot be empty.");
			valid = false;
		}
		if (training.getDuration() == 0) {
			logger.warn("Training duration cannot be 0 or empty.");
			valid = false;
		}
		if (training.getTraineeUsername() == null && training.getTraineeUsername().isEmpty()) {
			logger.warn("Trainee username cannot be empty.");
			valid = false;
		}
		if (training.getTrainerUsername() == null && training.getTrainerUsername().isEmpty()) {
			logger.warn("Trainer username cannot be empty.");
			valid = false;
		}
		if (training.getTrainingType() == null && training.getTrainingType().isEmpty()) {
			logger.warn("Training type name cannot be empty.");
			valid = false;
		}
		return valid;
	}

}
