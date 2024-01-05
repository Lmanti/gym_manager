package com.epam.projects.gym.infrastructure.mappers;

import java.util.List;

import com.epam.projects.gym.application.dto.TraineeAssignedDto;
import com.epam.projects.gym.application.dto.basics.TraineeBasicDto;
import com.epam.projects.gym.application.dto.fulls.TraineeFullDto;
import com.epam.projects.gym.application.dto.requests.TraineeRegister;
import com.epam.projects.gym.application.dto.requests.TraineeUpdate;
import com.epam.projects.gym.application.dto.responses.TraineeProfile;
import com.epam.projects.gym.application.dto.responses.TraineeUpdated;
import com.epam.projects.gym.application.dto.responses.UserCreated;

public interface TraineeMapper {
	
	public TraineeFullDto mapBasicToFull(TraineeBasicDto traineeBasicDto);
	
	public TraineeBasicDto mapRequestToBasic(TraineeRegister traineeFullDto);

	public TraineeBasicDto parseUpdateToBasic(TraineeBasicDto trainee, TraineeUpdate update);

	public List<TraineeAssignedDto> mapBasicsToAssigned(List<TraineeBasicDto> ids);

	public UserCreated mapCreationResponse(TraineeFullDto dto);

	public TraineeUpdated mapUpdateResponse(TraineeFullDto dto);

	public TraineeProfile mapProfileResponse(TraineeFullDto trainee);

}
