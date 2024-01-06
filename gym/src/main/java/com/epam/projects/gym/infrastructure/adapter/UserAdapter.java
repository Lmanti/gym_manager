package com.epam.projects.gym.infrastructure.adapter;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.epam.projects.gym.domain.entity.User;
import com.epam.projects.gym.domain.repository.UserRepository;
import com.epam.projects.gym.infrastructure.datasource.entity.UserEntity;
import com.epam.projects.gym.infrastructure.datasource.postgresql.repository.UserJpaRepository;
import com.epam.projects.gym.infrastructure.exception.DatabaseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserAdapter implements UserRepository {
	
	private final UserJpaRepository userJpaRepository;
	
	public UserAdapter(UserJpaRepository userJpaRepository) {
		this.userJpaRepository = userJpaRepository;
	}

	@Override
	public User findByUsername(String username) {
		try {
			Optional<UserEntity> foundUser = userJpaRepository.findByUsername(username);
			if (foundUser.isPresent()) {
				return foundUser.get().toDomain();
			} else {
				log.debug("User with username " + username + " not found.");
				return null;
			}
		} catch (Exception e) {
			log.error("Error while finding user by username: {}", username);
			throw new DatabaseException("Error while finding user by username", e);
		}
	}

}
