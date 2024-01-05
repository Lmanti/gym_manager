package com.epam.projects.gym.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Training entity.
 * @author lherreram
 *
 */
@Entity
public class Training implements Serializable {
	
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Trainer's ID.
	 */
	@Id
	@Column
	private UUID id;
	
	/**
	 * Registered trainee for this training.
	 */
	@Column
	private UUID traineeId;
	
	/**
	 * Designated trainer for this training.
	 */
	@Column
	private UUID trainerId;
	
	/**
	 * Training name.
	 */
	@Column
	private String name;
	
	/**
	 * Training type for this training.
	 */
	@Column
	private UUID trainingTypeId;
	
	/**
	 * Training date.
	 */
	@Column
	private Date trainingDate;
	
	/**
	 * Training duration.
	 */
	@Column
	private int duration;

	/**
	 * @param traineeId
	 * 		- Registered trainee for this training.
	 * @param trainerId
	 * 		- Designated trainer for this training.
	 * @param name
	 * 		- Training name.
	 * @param trainingTypeId
	 * 		- Training type for this training.
	 * @param trainingDate
	 * 		- Training date.
	 * @param duration
	 * 		- Training duration.
	 */
	public Training(UUID traineeId, UUID trainerId, String name, UUID trainingTypeId,
			Date trainingDate, int duration) {
		this.id = UUID.randomUUID();
		this.traineeId = traineeId;
		this.trainerId = trainerId;
		this.name = name;
		this.trainingTypeId = trainingTypeId;
		this.trainingDate = trainingDate;
		this.duration = duration;
	}

	/**
	 * @param id
	 * @param traineeId
	 * @param trainerId
	 * @param name
	 * @param trainingTypeId
	 * @param trainingDate
	 * @param duration
	 */
	@JsonCreator
	public Training(
			@JsonProperty("id") UUID id,
			@JsonProperty("traineeId") UUID traineeId,
			@JsonProperty("trainerId") UUID trainerId,
			@JsonProperty("name") String name,
			@JsonProperty("trainingTypeId") UUID trainingTypeId,
			@JsonProperty("trainingDate") Date trainingDate,
			@JsonProperty("duration") int duration) {
		this.id = id;
		this.traineeId = traineeId;
		this.trainerId = trainerId;
		this.name = name;
		this.trainingTypeId = trainingTypeId;
		this.trainingDate = trainingDate;
		this.duration = duration;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id
	 * 		- The id to set
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
	 * @param traineeId
	 * 		- The traineeId to set
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
	 * @param trainerId
	 * 		- The trainerId to set
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
	 * @param name
	 * 		- The name to set
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
	 * @param trainingTypeId
	 * 		- The trainingTypeId to set
	 */
	public void setTrainingTypeId(UUID trainingTypeId) {
		this.trainingTypeId = trainingTypeId;
	}

	/**
	 * @return the trainingDate
	 */
	public Date getTrainingDate() {
		return trainingDate;
	}

	/**
	 * @param trainingDate
	 * 		- The trainingDate to set
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
	 * @param duration
	 * 		- The duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

}
