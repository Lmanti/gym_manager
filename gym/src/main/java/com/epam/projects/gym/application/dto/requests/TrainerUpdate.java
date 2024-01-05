package com.epam.projects.gym.application.dto.requests;

public class TrainerUpdate extends TrainerRegister {
	
	/**
	 * Trainer's username.
	 */
	private String username;
	
	/**
	 * {@link Boolean true} if the user is active {@link Boolean false} if not.
	 */
	private boolean isActive;

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
