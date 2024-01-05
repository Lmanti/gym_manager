package com.epam.projects.gym.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Trainee entity
 * @author lherreram
 *
 */
@Entity
public class Trainee implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Trainee's ID.
	 */
	@Id
	@Column
	private UUID id;
	
	/**
	 * Trainee's date of birth.
	 */
	@Column
	private Date dateOfBirth;
	
	/**
	 * Trainee's address.
	 */
	@Column
	private String address;
	
	/**
	 * Trainee's user ID (FK).
	 */
	@Column
	private UUID userId;
	
	/**
	 * Trainers list
	 */
	@Column
	private List<UUID> trainers;

	/**
	 * Trainee constructor.
	 * @param userId
	 * 		- Trainee's user ID.
	 * @param dateOfBirth
	 * 		- Trainee's date of birth.
	 * @param address
	 * 		- Trainee's address.
	 */
	public Trainee(UUID userId, Date dateOfBirth, String address) {
		this.id = UUID.randomUUID();
		this.userId = userId;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.trainers = new ArrayList<>();
	}
	
	/**
	 * Trainee constructor for update.
	 * @param traineeId
	 * 		- Trainee's ID.
	 * @param userId
	 * 		- Trainee's user ID.
	 * @param dateOfBirth
	 * 		- Trainee's date of birth.
	 * @param address
	 * 		- Trainee's address.
	 */
	public Trainee(UUID traineeId, UUID userId, Date dateOfBirth, String address) {
		this.id = traineeId;
		this.userId = userId;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
	}
	
	/**
	 * Trainee constructor for testing.
	 * @param userId
	 * 		- Trainee's user ID.
	 */
	public Trainee(UUID userId) {
		this.id = UUID.randomUUID();
		this.userId = userId;
		this.trainers = new ArrayList<>();
	}

	/**
	 * Trainee's constructor for JSON Serialization.
	 * @param id
	 * @param dateOfBirth
	 * @param address
	 * @param userId
	 * @param trainers
	 */
	@JsonCreator
	public Trainee(
			@JsonProperty("id") UUID id,
			@JsonProperty("dateOfBirth") Date dateOfBirth,
			@JsonProperty("address") String address,
			@JsonProperty("userId") UUID userId,
			@JsonProperty("trainers") List<UUID> trainers) {
		this.id = id;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.userId = userId;
		this.trainers = trainers;
	}

	/**
	 * @return the Trainee id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id
	 * 		- The Trainee id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth
	 * 		- The dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 * 		- The address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the user entity of this trainee
	 */
	public UUID getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 * 		- The user entity to set
	 */
	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	/**
	 * @return the trainings
	 */
	public List<UUID> getTrainers() {
		return trainers;
	}

	/**
	 * @param trainings
	 * 		- The trainings to set
	 */
	public void setTrainers(List<UUID> trainers) {
		this.trainers = trainers;
	}

}
