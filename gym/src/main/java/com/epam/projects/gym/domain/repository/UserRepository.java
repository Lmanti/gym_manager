package com.epam.projects.gym.domain.repository;

import com.epam.projects.gym.domain.entity.User;

public interface UserRepository {
	
	public User findByUsername(String username);

}
