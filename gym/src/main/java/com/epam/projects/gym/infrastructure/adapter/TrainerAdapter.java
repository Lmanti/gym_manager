package com.epam.projects.gym.infrastructure.adapter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.projects.gym.domain.entity.Trainer;
import com.epam.projects.gym.domain.repository.TrainerRepository;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainerEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainingTypeEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.UserEntity;
import com.epam.projects.gym.infrastructure.datasource.postgresql.repository.TrainerJpaRepository;
import com.epam.projects.gym.infrastructure.exception.DatabaseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TrainerAdapter implements TrainerRepository {
	
	private TrainerJpaRepository trainerJpaRepository;
	
	public TrainerAdapter(TrainerJpaRepository trainerJpaRepository) {
		this.trainerJpaRepository = trainerJpaRepository;
	}
	
	@Override
	public List<Trainer> getAllTrainers() {
		try {
			List<TrainerEntity> foundTrainers = trainerJpaRepository.findAll();
			if (!foundTrainers.isEmpty()) {
				return foundTrainers.stream().map(TrainerEntity::toDomain).collect(Collectors.toList());			
			} else {
				return Collections.emptyList();
			}			
		} catch (Exception e) {
			log.error("Error while trying to fetch all Trainers from the database.", e);
			throw new DatabaseException("Error while trying to fetch all Trainers from the database.", e);
		}
	}

	@Override
	public Trainer findByUsername(String username) {
		try {
			Optional<TrainerEntity> foundTrainer = trainerJpaRepository.findByUserIdUsername(username);
			if (foundTrainer.isPresent()) {
				return foundTrainer.get().toDomain();
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error("Error while trying to fetch by username a Trainer from the database.", e);
			throw new DatabaseException("Error while trying to fetch by username a Trainer from the database.", e);
		}
	}

	@Transactional(rollbackFor = DatabaseException.class)
	@Override
	public Trainer createTrainer(Trainer newTrainer) {
		try {
			UserEntity newUser = new UserEntity(
					null,
					newTrainer.getUserId().getFirstName(),
					newTrainer.getUserId().getLastName(),
					newTrainer.getUserId().getUsername(),
					newTrainer.getUserId().getPassword(),
					newTrainer.getUserId().isActive(),
					null,
					null);
			
			TrainerEntity trainer = new TrainerEntity(
					null,
					new TrainingTypeEntity(
							newTrainer.getSpecialization().getId(),
							newTrainer.getSpecialization().getName(),
							null),
					newUser,
					null);
			
			newUser.setTrainerId(trainer);
			
			TrainerEntity createdTrainer = trainerJpaRepository.save(trainer);
			return createdTrainer.toDomain();
		} catch (Exception e) {
			log.error("Error while trying to register a Trainer.", e);
			throw new DatabaseException("Error while trying to register a Trainer.", e);
		}
	}

}
