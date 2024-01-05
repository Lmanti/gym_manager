package com.epam.projects.gym.application.dto;

public class TraineeAssignedDto {
	
	/**
	 * Trainee's username.
	 */
	private String username;
	
	/**
	 * Trainee's first name.
	 */
	private String firstName;
	
	/**
	 * Trainee's last name.
	 */
	private String lastName;

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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
