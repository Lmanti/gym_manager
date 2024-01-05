package com.epam.projects.gym.dto.fulls;

import java.util.List;
import java.util.UUID;

import com.epam.projects.gym.dto.TraineeAssignedDto;
import com.epam.projects.gym.dto.UserDto;

public class TrainerFullDto extends UserDto {
	
	/**
	 * Trainer's ID.
	 */
	private UUID trainerId;
	
	/**
	 * Trainer's specialization (FK).
	 */
	private String specialization;
	
	/**
	 * Trainees List
	 */
	private List<TraineeAssignedDto> trainees;

	/**
	 * @return the id
	 */
	public UUID getTrainerId() {
		return trainerId;
	}

	/**
	 * @param id the id to set
	 */
	public void setTrainerId(UUID id) {
		this.trainerId = id;
	}

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
