package com.epam.projects.gym.dto.requests;

import java.time.LocalDate;

public class TraineeTraining {
	
	/**
	 * Trainee's username.
	 */
	private String username;
	
	/**
	 * Training periodFrom.
	 */
	private LocalDate periodFrom;
	
	/**
	 * Training periodTo.
	 */
	private LocalDate periodTo;
	
	/**
	 * Trainer's Name.
	 */
	private String trainerName;
	
	/**
	 * Training type name.
	 */
	private String trainingType;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the periodFrom
	 */
	public LocalDate getPeriodFrom() {
		return periodFrom;
	}

	/**
	 * @param periodFrom the periodFrom to set
	 */
	public void setPeriodFrom(LocalDate periodFrom) {
		this.periodFrom = periodFrom;
	}

	/**
	 * @return the periodTo
	 */
	public LocalDate getPeriodTo() {
		return periodTo;
	}

	/**
	 * @param periodTo the periodTo to set
	 */
	public void setPeriodTo(LocalDate periodTo) {
		this.periodTo = periodTo;
	}

	/**
	 * @return the trainerName
	 */
	public String getTrainerName() {
		return trainerName;
	}

	/**
	 * @param trainerName the trainerName to set
	 */
	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
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

}
