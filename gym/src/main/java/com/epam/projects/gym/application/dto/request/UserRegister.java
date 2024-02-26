package com.epam.projects.gym.application.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRegister {

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;
	
}
