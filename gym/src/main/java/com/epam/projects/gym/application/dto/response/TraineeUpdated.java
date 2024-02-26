package com.epam.projects.gym.application.dto.response;

import java.util.List;

import com.epam.projects.gym.application.dto.TrainerAssignedDto;
import com.epam.projects.gym.application.dto.request.TraineeUpdate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraineeUpdated extends TraineeUpdate {
	
	private List<TrainerAssignedDto> trainers;
	
}
