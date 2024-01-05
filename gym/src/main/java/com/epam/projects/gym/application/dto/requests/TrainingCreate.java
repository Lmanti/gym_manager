package com.epam.projects.gym.application.dto.requests;

import java.time.LocalDate;

public class TrainingCreate {
	
	/**
	 * Trainee username.
	 */
	private String traineeUsername;
	
	/**
	 * Trainer username.
	 */
	private String trainerUsername;
	
	/**
	 * Training name.
	 */
	private String trainingName;
	
	/**
	 * Training date.
	 */
	private LocalDate trainingDate;
	
	/**
	 * Training type.
	 */
	private String trainingType;
	
	/**
	 * Training duration.
	 */
	private int duration;

	/**
	 * @return the traineeUsername
	 */
	public String getTraineeUsername() {
		return traineeUsername;
	}

	/**
	 * @param traineeUsername the traineeUsername to set
	 */
	public void setTraineeUsername(String traineeUsername) {
		this.traineeUsername = traineeUsername;
	}

	/**
	 * @return the trainerUsername
	 */
	public String getTrainerUsername() {
		return trainerUsername;
	}

	/**
	 * @param trainerUsername the trainerUsername to set
	 */
	public void setTrainerUsername(String trainerUsername) {
		this.trainerUsername = trainerUsername;
	}

	/**
	 * @return the trainingName
	 */
	public String getTrainingName() {
		return trainingName;
	}

	/**
	 * @param trainingName the trainingName to set
	 */
	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
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
	 * @return the trainingType
	 */
	public String getTrainingType() {
		return trainingType;
	}

	/**
	 * @param trainingType the trainingType to set
	 */
	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
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
