package com.epam.projects.gym.dto.requests;

public class ChangeLogin extends UserLogin {

	/**
	 * User new password.
	 */
	private String newPassword;

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
