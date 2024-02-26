package com.epam.projects.gym.domain.repository;

import java.util.Optional;

import com.epam.projects.gym.domain.entity.User;

public interface UserRepository {
	
	public Optional<User> findByUsername(String username);

}
