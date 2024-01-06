package com.epam.projects.gym.infrastructure.datasource.postgresql.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.projects.gym.infrastructure.datasource.entity.TrainerEntity;

@Repository
public interface TrainerJpaRepository extends JpaRepository<TrainerEntity, UUID> {

}
