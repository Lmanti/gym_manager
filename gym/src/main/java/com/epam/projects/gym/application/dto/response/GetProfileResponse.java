package com.epam.projects.gym.application.dto.response;

public class GetProfileResponse {
	
	/**
	 * User first name.
	 */
	private String firstName;
	
	/**
	 * User last name.
	 */
	private String lastName;
	
	/**
	 * {@link Boolean true} if the user is active {@link Boolean false} if not.
	 */
	private boolean isActive;

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
