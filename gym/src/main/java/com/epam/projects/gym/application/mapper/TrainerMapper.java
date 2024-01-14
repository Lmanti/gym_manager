package com.epam.projects.gym.application.mapper;

import java.util.stream.Collectors;

import com.epam.projects.gym.application.dto.TrainerAssignedDto;
import com.epam.projects.gym.application.dto.response.TrainerProfile;
import com.epam.projects.gym.application.dto.response.TrainerUpdated;
import com.epam.projects.gym.application.enums.Specialization;
import com.epam.projects.gym.domain.entity.Trainer;

public class TrainerMapper {

	public static final TrainerAssignedDto toAssignedDto(Trainer trainer) {
		TrainerAssignedDto dto = new TrainerAssignedDto();
		dto.setFirstName(trainer.getFirstName());
		dto.setLastName(trainer.getLastName());
		dto.setSpecialization(trainer.getSpecialization().getName());
		dto.setUsername(trainer.getUsername());
		return dto;
	}
	
	public static final TrainerProfile toProfile(Trainer trainer) {
		TrainerProfile trainerProfile = new TrainerProfile();
		trainerProfile.setFirstName(trainer.getFirstName());
		trainerProfile.setLastName(trainer.getLastName());
		trainerProfile.setSpecialization(trainer.getSpecialization().getName());
		trainerProfile.setActive(trainer.getIsActive());
		trainerProfile.setTrainees(trainer.getTrainees()
				.stream().map(TraineeMapper::toAssignedDto).collect(Collectors.toList()));
		return trainerProfile;
	}

	public static TrainerUpdated toUpdated(Trainer trainer) {
		TrainerUpdated updated = new TrainerUpdated();
		updated.setFirstName(trainer.getFirstName());
		updated.setLastName(trainer.getLastName());
		updated.setUsername(trainer.getUsername());
		updated.setActive(trainer.getIsActive());
		updated.setSpecialization(Specialization.identify(trainer.getSpecialization().getName()));
		updated.setTrainees(trainer.getTrainees()
				.stream().map(TraineeMapper::toAssignedDto).collect(Collectors.toList()));
		return updated;
	}
	
}
