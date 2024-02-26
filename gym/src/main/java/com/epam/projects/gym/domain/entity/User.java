package com.epam.projects.gym.domain.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {

	private String userId;

	private String firstName;

	private String lastName;

	private String username;

	private String password;

	private Boolean isActive;
	
	private Trainee traineeId;
	
	private Trainer trainerId;
	
	public User(
			@NonNull String firstName,
			@NonNull String lastName,
			@NonNull String username,
			@NonNull String password,
			@NonNull Boolean isActive
			) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.isActive = isActive;
	}

}
