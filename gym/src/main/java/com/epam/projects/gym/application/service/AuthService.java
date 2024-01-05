package com.epam.projects.gym.application.service;

import com.epam.projects.gym.application.dto.requests.ChangeLogin;
import com.epam.projects.gym.application.dto.requests.UserLogin;

public interface AuthService {
	
	public boolean login(UserLogin user);
	
	public boolean changeLogin(ChangeLogin user);

}
