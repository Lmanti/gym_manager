package com.epam.projects.gym.application.dto.basics;

import java.time.LocalDate;
import java.util.UUID;

public class TrainingBasicDto {

	/**
	 * Trainer's ID.
	 */
	private UUID id;
	
	/**
	 * Registered trainee for this training.
	 */
	private UUID traineeId;
	
	/**
	 * Designated trainer for this training.
	 */
	private UUID trainerId;
	
	/**
	 * Training name.
	 */
	private String name;
	
	/**
	 * Training type for this training.
	 */
	private UUID trainingTypeId;
	
	/**
	 * Training date.
	 */
	private LocalDate trainingDate;
	
	/**
	 * Training duration.
	 */
	private int duration;

	/**
	 * @param traineeId
	 * @param trainerId
	 * @param name
	 * @param trainingTypeId
	 * @param trainingDate
	 * @param duration
	 */
	public TrainingBasicDto(UUID traineeId, UUID trainerId, String name, UUID trainingTypeId, LocalDate trainingDate,
			int duration) {
		this.traineeId = traineeId;
		this.trainerId = trainerId;
		this.name = name;
		this.trainingTypeId = trainingTypeId;
		this.trainingDate = trainingDate;
		this.duration = duration;
	}
	
	public TrainingBasicDto() {
		
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
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
	 * @return the trainerId
	 */
	public UUID getTrainerId() {
		return trainerId;
	}

	/**
	 * @param trainerId the trainerId to set
	 */
	public void setTrainerId(UUID trainerId) {
		this.trainerId = trainerId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the trainingTypeId
	 */
	public UUID getTrainingTypeId() {
		return trainingTypeId;
	}

	/**
	 * @param trainingTypeId the trainingTypeId to set
	 */
	public void setTrainingTypeId(UUID trainingTypeId) {
		this.trainingTypeId = trainingTypeId;
	}

	/**
	 * @return the trainingDate
	 */
	public LocalDate getTrainingDate() {
		return trainingDate;
	}

	/**
	 * @param trainingDate the trainingDate to set
	 */
	public void setTrainingDate(LocalDate trainingDate) {
		this.trainingDate = trainingDate;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
}
