package com.epam.projects.gym.application.dto.full;

import java.util.Date;
import java.util.UUID;

public class TrainingFullDto {

	/**
	 * Trainer's ID.
	 */
	private UUID id;
	
	/**
	 * Registered trainee for this training.
	 */
	private TraineeFullDto traineeId;
	
	/**
	 * Designated trainer for this training.
	 */
	private TrainerFullDto trainerId;
	
	/**
	 * Training name.
	 */
	private String name;
	
	/**
	 * Training type for this training.
	 */
	private TrainingTypeFullDto trainingTypeId;
	
	/**
	 * Training date.
	 */
	private Date trainingDate;
	
	/**
	 * Training duration.
	 */
	private int duration;

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
	public TraineeFullDto getTraineeId() {
		return traineeId;
	}

	/**
	 * @param traineeId the traineeId to set
	 */
	public void setTraineeId(TraineeFullDto traineeId) {
		this.traineeId = traineeId;
	}

	/**
	 * @return the trainerId
	 */
	public TrainerFullDto getTrainerId() {
		return trainerId;
	}

	/**
	 * @param trainerId the trainerId to set
	 */
	public void setTrainerId(TrainerFullDto trainerId) {
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
	public TrainingTypeFullDto getTrainingTypeId() {
		return trainingTypeId;
	}

	/**
	 * @param trainingTypeId the trainingTypeId to set
	 */
	public void setTrainingTypeId(TrainingTypeFullDto trainingTypeId) {
		this.trainingTypeId = trainingTypeId;
	}

	/**
	 * @return the trainingDate
	 */
	public Date getTrainingDate() {
		return trainingDate;
	}

	/**
	 * @param trainingDate the trainingDate to set
	 */
	public void setTrainingDate(Date trainingDate) {
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
