package com.epam.projects.gym.domain.entity;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Training {

	private UUID id;

	private Trainee traineeId;

	private Trainer trainerId;

	private String name;

	private TrainingType trainingTypeId;

	private LocalDate trainingDate;

	private Integer duration;
	
	public Training(
			@NonNull Trainee traineeId,
			@NonNull Trainer trainerId,
			@NonNull String name,
			@NonNull TrainingType trainingTypeId,
			@NonNull LocalDate trainingDate,
			@NonNull Integer duration
			) {
		this.traineeId = traineeId;
		this.trainerId = trainerId;
		this.name = name;
		this.trainingTypeId = trainingTypeId;
		this.trainingDate = trainingDate;
		this.duration = duration;
	}
	
}
