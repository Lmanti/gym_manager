package com.epam.projects.gym.application.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTrainerList {

	@NotBlank
	private String username;
	
	@NotNull
	private List<String> trainerList;
	
}
