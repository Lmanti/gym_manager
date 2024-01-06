package com.epam.projects.gym.application.dto.response;

import java.util.List;

import com.epam.projects.gym.application.dto.TrainerAssignedDto;
import com.epam.projects.gym.application.dto.request.TraineeUpdate;

public class TraineeUpdated extends TraineeUpdate {
	
	private List<TrainerAssignedDto> trainers;

	/**
	 * @return the trainers
	 */
	public List<TrainerAssignedDto> getTrainers() {
		return trainers;
	}

	/**
	 * @param trainers the trainers to set
	 */
	public void setTrainers(List<TrainerAssignedDto> trainers) {
		this.trainers = trainers;
	}
	
}
