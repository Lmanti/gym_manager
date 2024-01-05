package com.epam.projects.gym.domain.entity;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

/**
 * Training entity.
 * @author lherreram
 *
 */
@Data
public class Training {

	/**
	 * Trainer's ID.
	 */
	private UUID id;
	
	/**
	 * Registered trainee for this training.
	 */
	private Trainee traineeId;
	
	/**
	 * Designated trainer for this training.
	 */
	private Trainer trainerId;
	
	/**
	 * Training name.
	 */
	private String name;
	
	/**
	 * Training type for this training.
	 */
	private TrainingType trainingTypeId;
	
	/**
	 * Training date.
	 */
	private LocalDate trainingDate;
	
	/**
	 * Training duration.
	 */
	private int duration;
	
}
