package com.epam.projects.gym.domain.entity;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Trainer extends User {

	private UUID id;
	
	private TrainingType specialization;
	
	private List<Trainee> trainees;
	
	public Trainer(
			@NonNull String firstName,
			@NonNull String lastName,
			@NonNull String username,
			@NonNull String password,
			@NonNull Boolean isActive,
			@NonNull TrainingType specialization) {
		super(firstName, lastName, username, password, isActive);
		this.specialization = specialization;
	}

}
