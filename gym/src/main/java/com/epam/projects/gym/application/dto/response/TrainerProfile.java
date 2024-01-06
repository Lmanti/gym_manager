package com.epam.projects.gym.application.dto.response;

import java.util.List;

import com.epam.projects.gym.application.dto.TraineeAssignedDto;

public class TrainerProfile extends GetProfileResponse {
	
	/**
	 * Trainer specialization.
	 */
	private String specialization;
	
	/**
	 * Trainees List
	 */
	private List<TraineeAssignedDto> trainees;

	/**
	 * @return the specialization
	 */
	public String getSpecialization() {
		return specialization;
	}

	/**
	 * @param specialization the specialization to set
	 */
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

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
