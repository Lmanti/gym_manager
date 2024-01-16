package com.epam.projects.gym.infrastructure.datasource.postgresql.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.epam.projects.gym.infrastructure.datasource.entity.TrainingEntity;

@Repository
public interface TrainingJpaRepository extends JpaRepository<TrainingEntity, UUID>, JpaSpecificationExecutor<TrainingEntity> {

}
