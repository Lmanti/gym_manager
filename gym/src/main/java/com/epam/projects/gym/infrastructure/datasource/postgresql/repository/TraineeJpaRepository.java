package com.epam.projects.gym.infrastructure.datasource.postgresql.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.projects.gym.infrastructure.datasource.entity.TraineeEntity;

public interface TraineeJpaRepository extends JpaRepository<TraineeEntity, UUID> {
	
	public Optional<TraineeEntity> findByUserIdUsername(String username);
	
}
