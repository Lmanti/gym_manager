package com.epam.projects.gym.application.dto.responses;

import java.time.LocalDate;
import java.util.List;

import com.epam.projects.gym.application.dto.TrainerAssignedDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public class TraineeProfile extends GetProfileResponse {
	
	/**
	 * Trainee date of birth.
	 */
	@ApiModelProperty(example = "2011-11-11")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;
	
	/**
	 * Trainee address.
	 */
	private String address;
	
	/**
	 * Trainers List
	 */
	private List<TrainerAssignedDto> trainers;

	/**
	 * @return the dateOfBirth
	 */
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

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
