package com.epam.projects.gym.application.dto.basic;

import java.util.List;
import java.util.UUID;

import com.epam.projects.gym.application.dto.UserDto;

public class TrainerBasicDto extends UserDto {
	
	/**
	 * Trainer's ID.
	 */
	private UUID trainerId;
	
	/**
	 * Trainer's specialization (FK).
	 */
	private String specialization;
	
	/**
	 * Trainees list
	 */
	private List<UUID> trainees;

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
	public List<UUID> getTrainees() {
		return trainees;
	}

	/**
	 * @param trainees the trainees to set
	 */
	public void setTrainees(List<UUID> trainees) {
		this.trainees = trainees;
	}

}
