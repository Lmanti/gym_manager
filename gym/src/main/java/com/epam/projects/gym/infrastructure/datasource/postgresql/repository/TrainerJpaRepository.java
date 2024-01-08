package com.epam.projects.gym.infrastructure.datasource.postgresql.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.projects.gym.infrastructure.datasource.entity.TrainerEntity;

public interface TrainerJpaRepository extends JpaRepository<TrainerEntity, UUID> {

	public Optional<TrainerEntity> findByUserIdUsername(String username);

}
