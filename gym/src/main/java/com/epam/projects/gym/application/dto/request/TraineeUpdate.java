package com.epam.projects.gym.application.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class TraineeUpdate extends TraineeRegister {
	
	@NotBlank
	private String username;
	
	@NotNull
	private boolean isActive;
	
}
