package com.epam.projects.gym.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeUserStatus {
	
	private String username;
	
	private boolean isActive;

}
