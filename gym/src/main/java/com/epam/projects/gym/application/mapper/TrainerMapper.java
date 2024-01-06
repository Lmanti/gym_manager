package com.epam.projects.gym.application.mapper;

import com.epam.projects.gym.application.dto.TrainerAssignedDto;
import com.epam.projects.gym.domain.entity.Trainer;

public class TrainerMapper {

	public static final TrainerAssignedDto toAssignedDto(Trainer trainee) {
		TrainerAssignedDto dto = new TrainerAssignedDto();
		dto.setFirstName(trainee.getUserId().getFirstName());
		dto.setLastName(trainee.getUserId().getLastName());
		dto.setSpecialization(trainee.getSpecialization().getName());
		dto.setUsername(trainee.getUserId().getUsername());
		return dto;
	}
	
}
