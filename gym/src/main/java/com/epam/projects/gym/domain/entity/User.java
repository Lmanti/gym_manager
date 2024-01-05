package com.epam.projects.gym.domain.entity;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {

	private UUID id;

	private String firstName;

	private String lastName;

	private String username;

	private String password;

	private boolean isActive;
	
	private Trainee traineeId;
	
	private Trainer trainerId;

}
