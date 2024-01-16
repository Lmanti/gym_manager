package com.epam.projects.gym.application.dto.response;

import java.util.List;

import com.epam.projects.gym.application.dto.TraineeAssignedDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerProfile extends GetProfileResponse {

	private String specialization;

	private List<TraineeAssignedDto> trainees;
	
}
