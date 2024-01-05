package com.epam.projects.gym.dto.responses;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public class Trainings2Trainers {
	
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
	 * Trainee's name.
	 */
	private String traineeName;

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

	/**
	 * @return the traineeName
	 */
	public String getTraineeName() {
		return traineeName;
	}

	/**
	 * @param traineeName the traineeName to set
	 */
	public void setTraineeName(String traineeName) {
		this.traineeName = traineeName;
	}

}
