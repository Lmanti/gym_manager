package com.epam.projects.gym.infrastructure.datasource.postgresql.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.projects.gym.infrastructure.datasource.entity.TrainingTypeEntity;

public interface TrainingTypeJpaRepository extends JpaRepository<TrainingTypeEntity, UUID> {
	
	public Optional<TrainingTypeEntity> findByName(String name);

}
