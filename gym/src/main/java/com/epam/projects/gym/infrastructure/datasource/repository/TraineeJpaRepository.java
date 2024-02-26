package com.epam.projects.gym.infrastructure.datasource.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.projects.gym.infrastructure.datasource.entity.TraineeEntity;

public interface TraineeJpaRepository extends JpaRepository<TraineeEntity, String> {
	
	public Optional<TraineeEntity> findByUserIdUsername(String username);
	
	public void deleteByUserIdUsername(String username);

	public boolean existsByUserIdUsername(String username);
	
}
