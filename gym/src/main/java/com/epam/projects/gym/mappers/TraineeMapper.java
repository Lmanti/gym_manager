package com.epam.projects.gym.mappers;

import java.util.List;

import com.epam.projects.gym.dto.TraineeAssignedDto;
import com.epam.projects.gym.dto.basics.TraineeBasicDto;
import com.epam.projects.gym.dto.fulls.TraineeFullDto;
import com.epam.projects.gym.dto.requests.TraineeRegister;
import com.epam.projects.gym.dto.requests.TraineeUpdate;
import com.epam.projects.gym.dto.responses.TraineeProfile;
import com.epam.projects.gym.dto.responses.TraineeUpdated;
import com.epam.projects.gym.dto.responses.UserCreated;

public interface TraineeMapper {
	
	public TraineeFullDto mapBasicToFull(TraineeBasicDto traineeBasicDto);
	
	public TraineeBasicDto mapRequestToBasic(TraineeRegister traineeFullDto);

	public TraineeBasicDto parseUpdateToBasic(TraineeBasicDto trainee, TraineeUpdate update);

	public List<TraineeAssignedDto> mapBasicsToAssigned(List<TraineeBasicDto> ids);

	public UserCreated mapCreationResponse(TraineeFullDto dto);

	public TraineeUpdated mapUpdateResponse(TraineeFullDto dto);

	public TraineeProfile mapProfileResponse(TraineeFullDto trainee);

}
