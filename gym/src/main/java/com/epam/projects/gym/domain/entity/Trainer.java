package com.epam.projects.gym.domain.entity;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Trainer {
	
	private UUID id;
	
	private TrainingType specialization;

	private User userId;
	
	private List<Trainee> trainees;

}
