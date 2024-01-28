package com.epam.projects.gym.application.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTrainerList {

	private String username;
	
	private List<String> trainerList;
	
}
