package com.epam.projects.gym.application.dto.requests;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public class TraineeRegister extends UserRegister {
	
	/**
	 * Trainee's date of birth.
	 */
	private LocalDate dateOfBirth;
	
	/**
	 * Trainee's address.
	 */
	private String address;

	/**
	 * @return the dateOfBirth
	 */
	@ApiModelProperty(example = "2011-11-11")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

}
