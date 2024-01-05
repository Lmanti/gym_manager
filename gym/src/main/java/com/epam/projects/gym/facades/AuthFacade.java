package com.epam.projects.gym.facades;

import com.epam.projects.gym.dto.requests.ChangeLogin;
import com.epam.projects.gym.dto.requests.UserLogin;

public interface AuthFacade {
	
	public boolean login(UserLogin user);
	
	public boolean changeLogin(ChangeLogin user);

}
