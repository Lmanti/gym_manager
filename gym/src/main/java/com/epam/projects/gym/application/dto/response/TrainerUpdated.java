package com.epam.projects.gym.application.dto.response;

import java.util.List;

import com.epam.projects.gym.application.dto.TraineeAssignedDto;
import com.epam.projects.gym.application.dto.request.TrainerUpdate;

public class TrainerUpdated extends TrainerUpdate {
	
	/**
	 * Trainees List
	 */
	private List<TraineeAssignedDto> trainees;

	/**
	 * @return the trainees
	 */
	public List<TraineeAssignedDto> getTrainees() {
		return trainees;
	}

	/**
	 * @param trainees the trainees to set
	 */
	public void setTrainees(List<TraineeAssignedDto> trainees) {
		this.trainees = trainees;
	}
	
}
