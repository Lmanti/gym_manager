package com.epam.projects.gym.application.mapper;

import java.util.stream.Collectors;

import com.epam.projects.gym.application.dto.response.TraineeProfile;
import com.epam.projects.gym.domain.entity.Trainee;

public class TraineeMapper {
	
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

}
