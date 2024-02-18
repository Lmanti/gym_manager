package com.epam.projects.gym.application.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeLogin extends UserLogin {

	@NotBlank
	private String newPassword;
	
}
