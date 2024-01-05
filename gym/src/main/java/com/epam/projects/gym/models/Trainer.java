package com.epam.projects.gym.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Trainer entity.
 * @author lherreram
 *
 */
@Entity
public class Trainer implements Serializable {

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
	 * Trainer's specialization (FK).
	 */
	@Column
	private UUID specialization;
	
	/**
	 * Trainer's user ID (FK).
	 */
	@Column
	private UUID userId;
	
	/**
	 * Trainees list
	 */
	@Column
	private List<UUID> trainees;

	/**
	 * @param userId
	 * 		- User entity for this trainer.
	 * @param specialization
	 * 		- Trainer's specialization.
	 */
	public Trainer(UUID userId, UUID specialization) {
		this.id = UUID.randomUUID();
		this.userId = userId;
		this.specialization = specialization;
		this.trainees = new ArrayList<>();
	}
	
	/**
	 * Trainer constructor for update.
	 * @param trainerId
	 * 		- Trainer ID
	 * @param userId
	 * 		- User entity for this trainer.
	 * @param specialization
	 * 		- Trainer's specialization.
	 */
	public Trainer(UUID trainerId, UUID userId, UUID specialization) {
		this.id = trainerId;
		this.userId = userId;
		this.specialization = specialization;
	}
	
	/**
	 * Trainer constructor for JSON serialization.
	 * @param trainerId
	 * 		- Trainer's ID.
	 * @param userId
	 * 		- Trainer's user ID.
	 * @param dateOfBirth
	 * 		- Trainer's date of birth.
	 * @param address
	 * 		- Trainer's address.
	 */
	@JsonCreator
	public Trainer(
			@JsonProperty("id") UUID trainerId,
			@JsonProperty("specialization") UUID specialization,
			@JsonProperty("userId") UUID userId,
			@JsonProperty("trainees") List<UUID> trainees) {
		this.id = trainerId;
		this.userId = userId;
		this.specialization = specialization;
		this.trainees = trainees;
	}
	
	
	
	/**
	 * Trainer constructor for testing.
	 * @param userId
	 * 		- Trainee's user ID.
	 */
	public Trainer(UUID userId) {
		this.id = UUID.randomUUID();
		this.userId = userId;
		this.trainees = new ArrayList<>();
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
	 * @return the specialization
	 */
	public UUID getSpecialization() {
		return specialization;
	}

	/**
	 * @param specialization
	 * 		- The specialization to set
	 */
	public void setSpecialization(UUID specialization) {
		this.specialization = specialization;
	}

	/**
	 * @return the userId
	 */
	public UUID getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 * 		- The userId to set
	 */
	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	/**
	 * @return the trainees
	 */
	public List<UUID> getTrainees() {
		return trainees;
	}

	/**
	 * @param trainees the trainees to set
	 */
	public void setTrainees(List<UUID> trainees) {
		this.trainees = trainees;
	}
	
}
