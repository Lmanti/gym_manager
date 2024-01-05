package com.epam.projects.gym.models;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User entity
 */
@Entity
public class User implements Serializable {
	
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * User ID.
	 */
	@Id
	@Column
	private UUID id;
	
	/**
	 * User first name.
	 */
	@Column
	private String firstName;
	
	/**
	 * User last name.
	 */
	@Column
	private String lastName;
	
	/**
	 * User username.
	 */
	@Column
	private String username;
	
	/**
	 * User password.
	 */
	@Column
	private String password;
	
	/**
	 * {@link Boolean true} if the user is active {@link Boolean false} if not.
	 */
	@Column
	private boolean isActive;
	
	/**
	 * User constructor.
	 * @param firstName
	 * 		- User first name.
	 * @param lastName
	 * 		- User last name.
	 * @param username
	 * 		- User username.
	 * @param password
	 * 		- User password.
	 * @param isActive
	 * 		- {@link Boolean true} if the user is active {@link Boolean false} if not.
	 */
	public User(String firstName, String lastName, boolean isActive) {
		this.id = UUID.randomUUID();
		this.firstName = firstName;
		this.lastName = lastName;
		this.isActive = isActive;
	}

	/**
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param password
	 * @param isActive
	 */
	@JsonCreator
	public User(
			@JsonProperty("id") UUID id,
			@JsonProperty("firstName") String firstName,
			@JsonProperty("lastName") String lastName,
			@JsonProperty("username") String username,
			@JsonProperty("password") String password,
			@JsonProperty("isActive") boolean isActive) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.isActive = isActive;
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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 * 		- The firstName to set
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
	 * @param lastName
	 * 		- The lastName to set
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
	 * @param username
	 * 		- The username to set
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
	 * @param password
	 * 		- The password to set
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
	 * @param isActive
	 * 		- The isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
