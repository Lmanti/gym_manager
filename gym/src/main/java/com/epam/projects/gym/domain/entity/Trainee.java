package com.epam.projects.gym.domain.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Trainee {

	private UUID id;
	
	private LocalDate dateOfBirth;
	
	private String address;
	
	private User userId;

	private List<Trainer> trainers;

}
