package com.epam.projects.gym.application.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTrainerList {

	@NotBlank
	private String username;
	
	@NotBlank
	private List<String> trainerList;
	
}
