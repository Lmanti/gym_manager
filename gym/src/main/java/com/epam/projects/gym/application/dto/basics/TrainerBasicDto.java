package com.epam.projects.gym.application.dto.basics;

import java.util.List;
import java.util.UUID;

import com.epam.projects.gym.application.dto.UserDto;

public class TrainerBasicDto extends UserDto {

	private UUID trainerId;
	
	private String specialization;
	
	private List<UUID> trainees;

	public UUID getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(UUID id) {
		this.trainerId = id;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public List<UUID> getTrainees() {
		return trainees;
	}

	public void setTrainees(List<UUID> trainees) {
		this.trainees = trainees;
	}

}
