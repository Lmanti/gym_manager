package com.epam.projects.gym.infrastructure.mappers.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.epam.projects.gym.application.dto.basics.TrainingBasicDto;
import com.epam.projects.gym.application.dto.requests.TrainingCreate;
import com.epam.projects.gym.infrastructure.datasource.entity.TraineeEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainerEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainingEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainingTypeEntity;
import com.epam.projects.gym.infrastructure.mappers.TrainingMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TrainingMapperImpl implements TrainingMapper {

	public List<TrainingBasicDto> parseTrainings(List<TrainingEntity> trainings) {
		try {
			List<TrainingBasicDto> dtoList = new ArrayList<>();
			trainings.stream().forEach(t -> {
				TrainingBasicDto dto = mapIdToTraining(t);
				dtoList.add(dto);
			});
			return dtoList;
		} catch (Exception e) {
			log.error("Error parsing the trainings list to DTO.", e);
			return Collections.emptyList();
		}
	}
	
	public TrainingBasicDto mapIdToTraining(TrainingEntity training) {
		TrainingBasicDto dto = new TrainingBasicDto();
		dto.setId(training.getTrainingId());
		dto.setTraineeId(training.getTraineeId().getTraineeId());
		dto.setTrainerId(training.getTrainerId().getTrainerId());
		dto.setTrainingTypeId(training.getTrainingTypeId().getTrainingTypeId());
		dto.setName(training.getName());
		dto.setTrainingDate(training.getTrainingDate());
		dto.setDuration(training.getDuration());
		return dto;
	}

	@Override
	public TrainingEntity mapDtoToTraining(TrainingBasicDto training) {
		if (validateTraining(training)) {
			TrainingEntity t = new TrainingEntity(
					UUID.randomUUID(),
					new TraineeEntity(training.getTraineeId(), null, null, null, null),
					new TrainerEntity(training.getTrainerId(), null, null, null),
					training.getName(),
					new TrainingTypeEntity(training.getTrainingTypeId(), null, null),
					training.getTrainingDate(),
					training.getDuration());
			return t;
		} else {
			log.error("Training is not valid, please check the info.");
			return null;
		}
	}
	
	private boolean validateTraining(TrainingBasicDto dto) {
		boolean valid = true;
		if (dto.getName() == null && dto.getName().isEmpty()) {
			log.error("Training name cannot be empty.");
			valid = false;
		}
		if (dto.getTrainingDate() == null) {
			log.error("Training date cannot be empty.");
			valid = false;
		}
		if (dto.getDuration() == 0) {
			log.error("Training duration cannot be empty.");
			valid = false;
		}
		return valid;
	}
	
	@Override
	public boolean validateCreate(TrainingCreate training) {
		boolean valid = true;
		if (training.getTrainingName() == null && training.getTrainingName().isEmpty()) {
			log.error("Training name cannot be empty.");
			valid = false;
		}
		if (training.getTrainingDate() == null) {
			log.error("Training date cannot be empty.");
			valid = false;
		}
		if (training.getDuration() == 0) {
			log.error("Training duration cannot be 0 or empty.");
			valid = false;
		}
		if (training.getTraineeUsername() == null && training.getTraineeUsername().isEmpty()) {
			log.error("Trainee username cannot be empty.");
			valid = false;
		}
		if (training.getTrainerUsername() == null && training.getTrainerUsername().isEmpty()) {
			log.error("Trainer username cannot be empty.");
			valid = false;
		}
		if (training.getTrainingType() == null && training.getTrainingType().isEmpty()) {
			log.error("Training type name cannot be empty.");
			valid = false;
		}
		return valid;
	}

}
