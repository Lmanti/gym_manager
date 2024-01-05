package com.epam.projects.gym.dto.fulls;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.epam.projects.gym.dto.TrainerAssignedDto;
import com.epam.projects.gym.dto.UserDto;

public class TraineeFullDto extends UserDto {
	
	/**
	 * Trainee's ID.
	 */
	private UUID traineeId;
	
	/**
	 * Trainee's date of birth.
	 */
	private LocalDate dateOfBirth;
	
	/**
	 * Trainee's address.
	 */
	private String address;
	
	/**
	 * Trainers list
	 */
	private List<TrainerAssignedDto> trainers = new ArrayList<>();

	/**
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param password
	 * @param dateOfBirth
	 * @param address
	 */
	public TraineeFullDto(String firstName, String lastName, String username, String password, LocalDate dateOfBirth,
			String address) {
		super(firstName, lastName, username, password);
		this.dateOfBirth = dateOfBirth;
		this.address = address;
	}
	
	public TraineeFullDto() {
		super();
	}

	/**
	 * @return the traineeId
	 */
	public UUID getTraineeId() {
		return traineeId;
	}

	/**
	 * @param traineeId the traineeId to set
	 */
	public void setTraineeId(UUID traineeId) {
		this.traineeId = traineeId;
	}

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
