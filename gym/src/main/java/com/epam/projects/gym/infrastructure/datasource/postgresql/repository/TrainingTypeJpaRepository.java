package com.epam.projects.gym.infrastructure.datasource.postgresql.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.projects.gym.infrastructure.datasource.entity.TrainingTypeEntity;

@Repository
public interface TrainingTypeJpaRepository extends JpaRepository<TrainingTypeEntity, UUID> {

}
