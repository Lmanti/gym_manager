package com.epam.projects.gym.domain.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class Trainee extends User {

	private UUID id;
	
	private LocalDate dateOfBirth;
	
	private String address;

	private List<Trainer> trainers;
	
	public Trainee(
			@NonNull String firstName,
			@NonNull String lastName,
			@NonNull String username,
			@NonNull String password,
			@NonNull Boolean isActive,
			@NonNull LocalDate dateOfBirth,
			@NonNull String address) {
		super(firstName, lastName, username, password, isActive);
		this.dateOfBirth = dateOfBirth;
		this.address = address;
	}

}
