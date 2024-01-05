package com.epam.projects.gym.application.dto.basics;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.epam.projects.gym.application.dto.UserDto;

public class TraineeBasicDto extends UserDto {
	
	private UUID traineeId;
	
	private LocalDate dateOfBirth;

	private String address;

	private List<UUID> trainers = new ArrayList<>();

	public TraineeBasicDto(String firstName, String lastName, String username, String password, LocalDate dateOfBirth,
			String address) {
		super(firstName, lastName, username, password);
		this.dateOfBirth = dateOfBirth;
		this.address = address;
	}
	
	public TraineeBasicDto() {
		super();
	}

	public UUID getTraineeId() {
		return traineeId;
	}

	public void setTraineeId(UUID traineeId) {
		this.traineeId = traineeId;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<UUID> getTrainers() {
		return trainers;
	}

	public void setTrainers(List<UUID> trainers) {
		this.trainers = trainers;
	}
	
}
