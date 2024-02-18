package com.epam.projects.gym.application.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeUserStatus {
	
	@NotBlank
	private String username;
	
	@NotNull
	private boolean isActive;

}
