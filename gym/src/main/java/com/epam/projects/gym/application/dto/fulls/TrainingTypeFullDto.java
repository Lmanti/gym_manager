package com.epam.projects.gym.application.dto.fulls;

import java.util.UUID;

public class TrainingTypeFullDto {
	
	/**
	 * Training type ID.
	 */
	private UUID id;
	
	/**
	 * Training type name.
	 */
	private String name;

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

}
