package com.epam.projects.gym.models;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Training type entity
 * @author lherreram
 *
 */
@Entity
public class TrainingType implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Training type ID.
	 */
	@Id
	@Column
	private UUID id;
	
	/**
	 * Training type name.
	 */
	@Column
	private String name;

	/**
	 * TrainingType constructor.
	 * @param name
	 * 		- Training type name.
	 */
	public TrainingType(String name) {
		this.id = UUID.randomUUID();
		this.name = name;
	}
	
	/**
	 * TrainingType constructor for JSON serialization.
	 * @param name
	 * 		- Training type name.
	 */
	@JsonCreator
	public TrainingType(@JsonProperty("id") UUID id,
						@JsonProperty("name") String name) {
		this.id = id;
		this.name = name;
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

}
