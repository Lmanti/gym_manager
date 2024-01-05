package com.epam.projects.gym.infrastructure.mappers.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.epam.projects.gym.application.dto.TraineeAssignedDto;
import com.epam.projects.gym.application.dto.basics.TraineeBasicDto;
import com.epam.projects.gym.application.dto.fulls.TraineeFullDto;
import com.epam.projects.gym.application.dto.requests.TraineeRegister;
import com.epam.projects.gym.application.dto.requests.TraineeUpdate;
import com.epam.projects.gym.application.dto.responses.TraineeProfile;
import com.epam.projects.gym.application.dto.responses.TraineeUpdated;
import com.epam.projects.gym.application.dto.responses.UserCreated;
import com.epam.projects.gym.infrastructure.mappers.TraineeMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TraineeMapperImpl implements TraineeMapper {

	@Override
	public TraineeFullDto mapBasicToFull(TraineeBasicDto traineeBasicDto) {
		TraineeFullDto dto = new TraineeFullDto();
		dto.setId(traineeBasicDto.getId());
		dto.setTraineeId(traineeBasicDto.getTraineeId());
		dto.setFirstName(traineeBasicDto.getFirstName());
		dto.setLastName(traineeBasicDto.getLastName());
		dto.setUsername(traineeBasicDto.getUsername());
		dto.setPassword(traineeBasicDto.getPassword());
		dto.setDateOfBirth(traineeBasicDto.getDateOfBirth());
		dto.setAddress(traineeBasicDto.getAddress());
		dto.setActive(traineeBasicDto.isActive());
		return dto;
	}

	@Override
	public TraineeBasicDto mapRequestToBasic(TraineeRegister trainee) {
		TraineeBasicDto dto = new TraineeBasicDto();
		dto.setFirstName(trainee.getFirstName());
		dto.setLastName(trainee.getLastName());
		dto.setDateOfBirth(trainee.getDateOfBirth());
		dto.setAddress(trainee.getAddress());
		return dto;
	}

	@Override
	public TraineeBasicDto parseUpdateToBasic(TraineeBasicDto trainee, TraineeUpdate update) {
		if (validateUpdate(update)) {
			trainee.setFirstName(update.getFirstName());
			trainee.setLastName(update.getLastName());
			trainee.setDateOfBirth(update.getDateOfBirth());
			trainee.setAddress(update.getAddress());
			trainee.setActive(update.isActive());
			return trainee;			
		} else {
			return null;
		}
	}
	
	@Override
	public List<TraineeAssignedDto> mapBasicsToAssigned(List<TraineeBasicDto> ids) {
		return ids.stream().map(x -> {
			TraineeAssignedDto dto = new TraineeAssignedDto();
			dto.setUsername(x.getUsername());
			dto.setFirstName(x.getFirstName());
			dto.setLastName(x.getLastName());
			return dto;
		}).collect(Collectors.toList());
	}
	
	@Override
	public UserCreated mapCreationResponse(TraineeFullDto dto) {
		UserCreated response = new UserCreated();
		response.setUsername(dto.getUsername());
		response.setPassword(dto.getPassword());
		return response;
	}
	
	@Override
	public TraineeUpdated mapUpdateResponse(TraineeFullDto dto) {
		TraineeUpdated response = new TraineeUpdated();
		response.setUsername(dto.getUsername());
		response.setFirstName(dto.getFirstName());
		response.setLastName(dto.getLastName());
		response.setDateOfBirth(dto.getDateOfBirth());
		response.setActive(dto.isActive());
		response.setAddress(dto.getAddress());
		response.setTrainers(dto.getTrainers());
		return response;
	}
	
	@Override
	public TraineeProfile mapProfileResponse(TraineeFullDto trainee) {
		TraineeProfile response = new TraineeProfile();
		response.setFirstName(trainee.getFirstName());
		response.setLastName(trainee.getLastName());
		response.setDateOfBirth(trainee.getDateOfBirth());
		response.setActive(trainee.isActive());
		response.setAddress(trainee.getAddress());
		response.setTrainers(trainee.getTrainers());
		return response;
	}
	
	private boolean validateUpdate(TraineeUpdate update) {
		boolean valid = true;
		
		if (update.getUsername() == null || update.getUsername().isEmpty()) {
			log.warn("User username cannot be empty.");
			valid = false;
		}
		if (update.getFirstName() == null || update.getFirstName().isEmpty()) {
			log.warn("User first name cannot be empty.");
			valid = false;
		}
		if (update.getLastName() == null || update.getLastName().isEmpty()) {
			log.warn("User last name cannot be empty.");
			valid = false;
		}
		if (update.getDateOfBirth() == null) {
			log.warn("Trainee date of birth name cannot be empty.");
			valid = false;
		}
		if (update.getAddress() == null || update.getFirstName().isEmpty()) {
			log.warn("Trainee address name cannot be empty.");
			valid = false;
		}
		
		return valid;
	}

}
