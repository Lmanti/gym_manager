package com.epam.projects.gym.mappers;

import java.util.List;

import com.epam.projects.gym.dto.TrainerAssignedDto;
import com.epam.projects.gym.dto.basics.TrainerBasicDto;
import com.epam.projects.gym.dto.fulls.TrainerFullDto;
import com.epam.projects.gym.dto.requests.TrainerRegister;
import com.epam.projects.gym.dto.requests.TrainerUpdate;
import com.epam.projects.gym.dto.responses.TrainerProfile;
import com.epam.projects.gym.dto.responses.TrainerUpdated;
import com.epam.projects.gym.dto.responses.UserCreated;

public interface TrainerMapper {

	public TrainerFullDto mapBasicToFull(TrainerBasicDto basic);
	
	public boolean validateRegister(TrainerRegister trainer);

	public UserCreated mapCreationResponse(TrainerFullDto dto);

	public TrainerProfile mapProfileResponse(TrainerFullDto dto);

	public TrainerBasicDto parseUpdateToBasic(TrainerBasicDto original, TrainerUpdate trainer);

	public TrainerUpdated mapUpdateResponse(TrainerFullDto dto);

	public List<TrainerAssignedDto> mapBasicsToAssigned(List<TrainerBasicDto> ids);

}
