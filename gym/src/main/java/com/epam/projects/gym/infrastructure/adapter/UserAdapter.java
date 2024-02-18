package com.epam.projects.gym.infrastructure.adapter;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.projects.gym.domain.entity.User;
import com.epam.projects.gym.domain.repository.UserRepository;
import com.epam.projects.gym.infrastructure.datasource.entity.UserEntity;
import com.epam.projects.gym.infrastructure.datasource.postgresql.repository.UserJpaRepository;
import com.epam.projects.gym.infrastructure.exception.DatabaseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserAdapter implements UserRepository {
	
	private UserJpaRepository userJpaRepository;
	
	public UserAdapter(UserJpaRepository userJpaRepository) {
		this.userJpaRepository = userJpaRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<User> findByUsername(String username) {
		try {
			log.debug("Looking for user with username: {}", username);
			Optional<UserEntity> foundUser = userJpaRepository.findByUsername(username);
			if (foundUser.isPresent()) {
				log.debug("User with username {} found successfully.", username);
				return Optional.of(foundUser.get().toDomain());
			} else {
				log.debug("Doesn't exist an user with username: {}.", username);
				return Optional.empty();			
			}			
		} catch (Exception exception) {
			throw new DatabaseException("Error while trying to find an user by username.", exception);
		}
	}

}
