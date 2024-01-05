package com.epam.projects.gym.mappers;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.epam.projects.gym.dto.TraineeAssignedDto;
import com.epam.projects.gym.dto.basics.TraineeBasicDto;
import com.epam.projects.gym.dto.fulls.TraineeFullDto;
import com.epam.projects.gym.dto.requests.TraineeRegister;
import com.epam.projects.gym.dto.requests.TraineeUpdate;
import com.epam.projects.gym.dto.responses.TraineeProfile;
import com.epam.projects.gym.dto.responses.TraineeUpdated;
import com.epam.projects.gym.dto.responses.UserCreated;

@Component
public class TraineeMapperImpl implements TraineeMapper {
	
	/**
	 * Logger for TraineeMapperImpl.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TraineeMapperImpl.class);

	@Override
	public TraineeFullDto mapBasicToFull(TraineeBasicDto traineeBasicDto) {
		TraineeFullDto dto = new TraineeFullDto();
		dto.setId(traineeBasicDto.getId());
		dto.setTraineeId(traineeBasicDto.getTraineeId());
		dto.setFirstName(traineeBasicDto.getFirstName());
		dto.setLastName(traineeBasicDto.getLastName());
		dto.setUsername(traineeBasicDto.getUsername());
		dto.setPassword(traineeBasicDto.getPassword());
		dto.setDateOfBirth(traineeBasicDto.getDateOfBirth().toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate());
		dto.setAddress(traineeBasicDto.getAddress());
		dto.setActive(traineeBasicDto.isActive());
		return dto;
	}

	@Override
	public TraineeBasicDto mapRequestToBasic(TraineeRegister trainee) {
		TraineeBasicDto dto = new TraineeBasicDto();
		dto.setFirstName(trainee.getFirstName());
		dto.setLastName(trainee.getLastName());
		dto.setDateOfBirth(trainee.getDateOfBirth() != null
				? Date.from(trainee.getDateOfBirth().atStartOfDay(ZoneId.systemDefault()).toInstant())
						: null);
		dto.setAddress(trainee.getAddress());
		return dto;
	}

	@Override
	public TraineeBasicDto parseUpdateToBasic(TraineeBasicDto trainee, TraineeUpdate update) {
		if (validateUpdate(update)) {
			trainee.setFirstName(update.getFirstName());
			trainee.setLastName(update.getLastName());
			trainee.setDateOfBirth(update.getDateOfBirth() != null
					? Date.from(update.getDateOfBirth().atStartOfDay(ZoneId.systemDefault()).toInstant())
							: null);
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
			logger.warn("User username cannot be empty.");
			valid = false;
		}
		if (update.getFirstName() == null || update.getFirstName().isEmpty()) {
			logger.warn("User first name cannot be empty.");
			valid = false;
		}
		if (update.getLastName() == null || update.getLastName().isEmpty()) {
			logger.warn("User last name cannot be empty.");
			valid = false;
		}
		if (update.getDateOfBirth() == null) {
			logger.warn("Trainee date of birth name cannot be empty.");
			valid = false;
		}
		if (update.getAddress() == null || update.getFirstName().isEmpty()) {
			logger.warn("Trainee address name cannot be empty.");
			valid = false;
		}
		
		return valid;
	}

}
