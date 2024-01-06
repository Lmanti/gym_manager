package com.epam.projects.gym.infrastructure.datasource.postgresql.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.projects.gym.infrastructure.datasource.entity.UserEntity;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {

	public Optional<UserEntity> findByUsername(String username);
	
}
