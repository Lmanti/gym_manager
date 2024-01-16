package com.epam.projects.gym.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeLogin extends UserLogin {

	private String newPassword;
	
}
