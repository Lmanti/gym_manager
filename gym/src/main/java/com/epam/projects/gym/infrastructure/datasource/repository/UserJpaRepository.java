package com.epam.projects.gym.infrastructure.datasource.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.projects.gym.infrastructure.datasource.entity.UserEntity;


@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
	
	public Optional<UserEntity> findByUsername(String username);
	
}
