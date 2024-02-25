package com.epam.projects.gym.infrastructure.datasource.postgresql.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.epam.projects.gym.infrastructure.datasource.entity.TrainerEntity;

public interface TrainerJpaRepository extends JpaRepository<TrainerEntity, String> {

	public Optional<TrainerEntity> findByUserIdUsername(String username);

	public boolean existsByUserIdUsername(String username);

	@Query("SELECT tr "
			+ "FROM TrainerEntity tr "
			+ "LEFT JOIN tr.trainees te "
			+ "LEFT JOIN te.userId teu "
			+ "INNER JOIN tr.userId tru "
			+ "WHERE tru.isActive = true "
			+ "AND (teu.username IS NULL OR teu.username <> :username)")
	public List<TrainerEntity> findAllNonAssociated(@Param("username") String username);


}
