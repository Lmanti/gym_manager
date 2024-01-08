package com.epam.projects.gym.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetProfileResponse {

	private String firstName;

	private String lastName;
	
	private boolean isActive;
	
}
