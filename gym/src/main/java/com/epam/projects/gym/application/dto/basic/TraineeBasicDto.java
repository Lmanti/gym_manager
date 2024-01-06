package com.epam.projects.gym.application.dto.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.epam.projects.gym.application.dto.UserDto;

public class TraineeBasicDto extends UserDto {
	
	/**
	 * Trainee's ID.
	 */
	private UUID traineeId;
	
	/**
	 * Trainee's date of birth.
	 */
	private Date dateOfBirth;
	
	/**
	 * Trainee's address.
	 */
	private String address;
	
	/**
	 * Trainers list
	 */
	private List<UUID> trainers = new ArrayList<>();

	/**
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param password
	 * @param dateOfBirth
	 * @param address
	 */
	public TraineeBasicDto(String firstName, String lastName, String username, String password, Date dateOfBirth,
			String address) {
		super(firstName, lastName, username, password);
		this.dateOfBirth = dateOfBirth;
		this.address = address;
	}
	
	public TraineeBasicDto() {
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
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
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
	public List<UUID> getTrainers() {
		return trainers;
	}

	/**
	 * @param trainers the trainers to set
	 */
	public void setTrainers(List<UUID> trainers) {
		this.trainers = trainers;
	}
	
}
