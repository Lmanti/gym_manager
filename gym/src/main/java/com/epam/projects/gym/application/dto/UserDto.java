package com.epam.projects.gym.application.dto;

import java.util.UUID;

public class UserDto {
	
	/**
	 * User ID.
	 */
	private UUID id;
	
	/**
	 * User first name.
	 */
	private String firstName;
	
	/**
	 * User last name.
	 */
	private String lastName;
	
	/**
	 * User username.
	 */
	private String username;
	
	/**
	 * User password.
	 */
	private String password;
	
	/**
	 * {@link Boolean true} if the user is active {@link Boolean false} if not.
	 */
	private boolean isActive;

	/**
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param password
	 */
	public UserDto(String firstName, String lastName, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * @param firstName
	 * @param lastName
	 */
	public UserDto(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public UserDto() {
		
	}

	/**
	 * @return the User id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the User id to set
	 */
	public void setId(UUID id) {
		this.id = id;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
