package com.epam.projects.gym.application.mapper;

import java.util.stream.Collectors;

import com.epam.projects.gym.application.dto.TraineeAssignedDto;
import com.epam.projects.gym.application.dto.response.TraineeProfile;
import com.epam.projects.gym.application.dto.response.TraineeUpdated;
import com.epam.projects.gym.domain.entity.Trainee;

public class TraineeMapper {
	
	public static final TraineeAssignedDto toAssignedDto(Trainee trainee) {
		TraineeAssignedDto dto = new TraineeAssignedDto();
		dto.setUsername(trainee.getUsername());
		dto.setFirstName(trainee.getFirstName());
		dto.setLastName(trainee.getLastName());
		return dto;
	}
	
	public static final TraineeProfile toProfile(Trainee trainee) {
		TraineeProfile profile = new TraineeProfile();
		profile.setFirstName(trainee.getFirstName());
		profile.setLastName(trainee.getLastName());
		profile.setDateOfBirth(trainee.getDateOfBirth());
		profile.setAddress(trainee.getAddress());
		profile.setActive(trainee.getIsActive());
		profile.setTrainers(trainee.getTrainers()
				.stream()
				.map(TrainerMapper::toAssignedDto)
				.collect(Collectors.toList()));
		return profile;
	}
	
	public static final TraineeUpdated toUpdated(Trainee trainee) {
		TraineeUpdated updated = new TraineeUpdated();
		updated.setUsername(trainee.getUsername());
		updated.setFirstName(trainee.getFirstName());
		updated.setLastName(trainee.getLastName());
		updated.setDateOfBirth(trainee.getDateOfBirth());
		updated.setAddress(trainee.getAddress());
		updated.setActive(trainee.getIsActive());
		updated.setTrainers(trainee.getTrainers()
				.stream()
				.map(TrainerMapper::toAssignedDto)
				.collect(Collectors.toList()));
		return updated;
	}

}
