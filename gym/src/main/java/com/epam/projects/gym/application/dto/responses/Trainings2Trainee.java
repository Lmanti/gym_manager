package com.epam.projects.gym.application.dto.responses;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public class Trainings2Trainee {
	
	/**
	 * Trainer's name.
	 */
	private String trainerName;
	
	/**
	 * Training date.
	 */
	@ApiModelProperty(example = "2011-11-11")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate trainingDate;
	
	/**
	 * Training type name.
	 */
	private String trainingType;
	
	/**
	 * Training duration.
	 */
	private int duration;
	
	/**
	 * Training name.
	 */
	private String trainingName;

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

}
