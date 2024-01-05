package com.epam.projects.gym.application.dto;

public class TrainerAssignedDto extends TraineeAssignedDto {

	/**
	 * Trainer's specialization.
	 */
	private String specialization;

	/**
	 * @return the specialization
	 */
	public String getSpecialization() {
		return specialization;
	}

	/**
	 * @param specialization the specialization to set
	 */
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	
}
