package com.epam.projects.gym.application.mapper;

import java.util.stream.Collectors;

import com.epam.projects.gym.application.dto.TrainerAssignedDto;
import com.epam.projects.gym.application.dto.response.TrainerProfile;
import com.epam.projects.gym.domain.entity.Trainer;

public class TrainerMapper {

	public static final TrainerAssignedDto toAssignedDto(Trainer trainer) {
		TrainerAssignedDto dto = new TrainerAssignedDto();
		dto.setFirstName(trainer.getUserId().getFirstName());
		dto.setLastName(trainer.getUserId().getLastName());
		dto.setSpecialization(trainer.getSpecialization().getName());
		dto.setUsername(trainer.getUserId().getUsername());
		return dto;
	}
	
	public static final TrainerProfile toProfile(Trainer trainer) {
		TrainerProfile trainerProfile = new TrainerProfile();
		trainerProfile.setFirstName(trainer.getUserId().getFirstName());
		trainerProfile.setLastName(trainer.getUserId().getLastName());
		trainerProfile.setSpecialization(trainer.getSpecialization().getName());
		trainerProfile.setActive(trainer.getUserId().isActive());
		trainerProfile.setTrainees(trainer.getTrainees()
				.stream().map(TraineeMapper::toAssignedDto).collect(Collectors.toList()));
		return trainerProfile;
	}
	
}
