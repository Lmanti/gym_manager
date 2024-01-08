package com.epam.projects.gym.application.mapper;

import java.util.stream.Collectors;

import com.epam.projects.gym.application.dto.TraineeAssignedDto;
import com.epam.projects.gym.application.dto.response.TraineeProfile;
import com.epam.projects.gym.application.dto.response.TraineeUpdated;
import com.epam.projects.gym.domain.entity.Trainee;

public class TraineeMapper {
	
	public static final TraineeAssignedDto toAssignedDto(Trainee trainee) {
		TraineeAssignedDto dto = new TraineeAssignedDto();
		dto.setUsername(trainee.getUserId().getUsername());
		dto.setFirstName(trainee.getUserId().getFirstName());
		dto.setLastName(trainee.getUserId().getLastName());
		return dto;
	}
	
	public static final TraineeProfile toProfile(Trainee trainee) {
		TraineeProfile profile = new TraineeProfile();
		profile.setFirstName(trainee.getUserId().getFirstName());
		profile.setLastName(trainee.getUserId().getLastName());
		profile.setDateOfBirth(trainee.getDateOfBirth());
		profile.setAddress(trainee.getAddress());
		profile.setActive(trainee.getUserId().isActive());
		profile.setTrainers(trainee.getTrainers()
				.stream()
				.map(TrainerMapper::toAssignedDto)
				.collect(Collectors.toList()));
		return profile;
	}
	
	public static final TraineeUpdated toUpdated(Trainee trainee) {
		TraineeUpdated updated = new TraineeUpdated();
		updated.setUsername(trainee.getUserId().getUsername());
		updated.setFirstName(trainee.getUserId().getFirstName());
		updated.setLastName(trainee.getUserId().getLastName());
		updated.setDateOfBirth(trainee.getDateOfBirth());
		updated.setAddress(trainee.getAddress());
		updated.setActive(trainee.getUserId().isActive());
		updated.setTrainers(trainee.getTrainers()
				.stream()
				.map(TrainerMapper::toAssignedDto)
				.collect(Collectors.toList()));
		return updated;
	}

}
