package com.epam.projects.gym.application.dto.response;

import java.util.List;

import com.epam.projects.gym.application.dto.TraineeAssignedDto;
import com.epam.projects.gym.application.dto.request.TrainerUpdate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerUpdated extends TrainerUpdate {
	
	private List<TraineeAssignedDto> trainees;
	
}
