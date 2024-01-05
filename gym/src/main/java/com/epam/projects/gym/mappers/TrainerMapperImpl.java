package com.epam.projects.gym.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.epam.projects.gym.dto.TrainerAssignedDto;
import com.epam.projects.gym.dto.basics.TrainerBasicDto;
import com.epam.projects.gym.dto.fulls.TrainerFullDto;
import com.epam.projects.gym.dto.requests.TrainerRegister;
import com.epam.projects.gym.dto.requests.TrainerRegister.Specialization;
import com.epam.projects.gym.dto.requests.TrainerUpdate;
import com.epam.projects.gym.dto.responses.TrainerProfile;
import com.epam.projects.gym.dto.responses.TrainerUpdated;
import com.epam.projects.gym.dto.responses.UserCreated;

@Component
public class TrainerMapperImpl implements TrainerMapper {
	
	/**
	 * Logger for TrainerMapperImpl.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TrainerMapperImpl.class);

	@Override
	public TrainerFullDto mapBasicToFull(TrainerBasicDto basic) {
		TrainerFullDto dto = new TrainerFullDto();
		dto.setId(basic.getId());
		dto.setTrainerId(basic.getTrainerId());
		dto.setFirstName(basic.getFirstName());
		dto.setLastName(basic.getLastName());
		dto.setActive(basic.isActive());
		dto.setSpecialization(basic.getSpecialization());
		dto.setUsername(basic.getUsername());
		dto.setPassword(basic.getPassword());
		return dto;
	}
	
	@Override
	public UserCreated mapCreationResponse(TrainerFullDto dto) {
		UserCreated response = new UserCreated();
		response.setUsername(dto.getUsername());
		response.setPassword(dto.getPassword());
		return response;
	}
	
	@Override
	public TrainerProfile mapProfileResponse(TrainerFullDto dto) {
		TrainerProfile response = new TrainerProfile();
		response.setFirstName(dto.getFirstName());
		response.setLastName(dto.getLastName());
		response.setSpecialization(dto.getSpecialization());
		response.setActive(dto.isActive());
		response.setTrainees(dto.getTrainees());
		return response;
	}
	
	@Override
	public TrainerBasicDto parseUpdateToBasic(TrainerBasicDto original, TrainerUpdate trainer) {
		original.setFirstName(trainer.getFirstName());
		original.setLastName(trainer.getLastName());
		original.setActive(trainer.isActive());
		return original;
	}
	
	@Override
	public TrainerUpdated mapUpdateResponse(TrainerFullDto dto) {
		TrainerUpdated response = new TrainerUpdated();
		response.setUsername(dto.getUsername());
		response.setFirstName(dto.getFirstName());
		response.setLastName(dto.getLastName());
		response.getSpecialization();
		response.setSpecialization(Specialization.valueOf(dto.getSpecialization()));
		response.setTrainees(dto.getTrainees());
		response.setActive(dto.isActive());
		return response;
	}
	
	@Override
	public List<TrainerAssignedDto> mapBasicsToAssigned(List<TrainerBasicDto> ids) {
		return ids.stream()
				.map(x -> {
					TrainerAssignedDto dto = new TrainerAssignedDto();
					dto.setUsername(x.getUsername());
					dto.setFirstName(x.getFirstName());
					dto.setLastName(x.getLastName());
					dto.setSpecialization(x.getSpecialization());
					return dto;
				}).collect(Collectors.toList());
	}
	
	public boolean validateRegister(TrainerRegister trainer) {
		boolean valid = true;
		
		if (trainer.getSpecialization() == null) {
			logger.warn("Trainer specialization cannot be empty or null.");
			valid = false;
		}
		
		return valid;
	}

}
