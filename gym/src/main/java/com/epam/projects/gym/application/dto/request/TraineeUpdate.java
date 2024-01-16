package com.epam.projects.gym.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraineeUpdate extends TraineeRegister {
	
	private String username;
	
	private boolean isActive;
	
}
