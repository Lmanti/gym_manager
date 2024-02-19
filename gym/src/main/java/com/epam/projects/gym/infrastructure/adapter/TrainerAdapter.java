package com.epam.projects.gym.infrastructure.adapter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.projects.gym.domain.entity.Trainer;
import com.epam.projects.gym.domain.repository.TrainerRepository;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainerEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainingTypeEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.UserEntity;
import com.epam.projects.gym.infrastructure.datasource.postgresql.repository.TrainerJpaRepository;
import com.epam.projects.gym.infrastructure.datasource.postgresql.repository.TrainingTypeJpaRepository;
import com.epam.projects.gym.infrastructure.exception.DatabaseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TrainerAdapter implements TrainerRepository {
	
	private TrainerJpaRepository trainerJpaRepository;
	
	private TrainingTypeJpaRepository trainingTypeJpaRepository;
	
	private BCryptPasswordEncoder passwordEncoder;
	
	public TrainerAdapter(
			TrainerJpaRepository trainerJpaRepository,
			TrainingTypeJpaRepository trainingTypeJpaRepository,
			BCryptPasswordEncoder passwordEncoder
			) {
		this.trainerJpaRepository = trainerJpaRepository;
		this.trainingTypeJpaRepository = trainingTypeJpaRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional(readOnly = true)
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
			log.debug("Error while trying to fetch all Trainers from the database.", e);
			throw new DatabaseException("Error while trying to fetch all Trainers from the database.", e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Trainer> findByUsername(String username) {
		try {
			Optional<TrainerEntity> foundTrainer = trainerJpaRepository.findByUserIdUsername(username);
			if (foundTrainer.isPresent()) {
				return Optional.of(foundTrainer.get().toDomain());
			} else {
				return Optional.empty();
			}
		} catch (Exception e) {
			log.debug("Error while trying to fetch by username a Trainer from the database.", e);
			throw new DatabaseException("Error while trying to fetch by username a Trainer from the database.", e);
		}
	}

	@Transactional(rollbackFor = DatabaseException.class)
	@Override
	public Optional<Trainer> createTrainer(Trainer newTrainer) {
		log.debug("Creating trainer: {}", newTrainer);
		try {
			Optional<TrainingTypeEntity> trainingType = trainingTypeJpaRepository.findById(newTrainer.getSpecialization().getId());
			
			UserEntity newUser = new UserEntity(
					newTrainer.getFirstName(),
					newTrainer.getLastName(),
					newTrainer.getUsername(),
					passwordEncoder.encode(newTrainer.getPassword()),
					newTrainer.getIsActive());
			
			TrainerEntity trainer = new TrainerEntity(
					trainingType.get(),
					newUser);
			
			newUser.setTrainerId(trainer);
			
			TrainerEntity createdTrainer = trainerJpaRepository.save(trainer);
			log.debug("Trainer created successfully with ID: {}", createdTrainer.getTrainerId());
			return Optional.of(createdTrainer.toDomain());
		} catch (Exception e) {
			log.debug("Error while trying to register a Trainer.", e);
			throw new DatabaseException("Error while trying to register a Trainer.", e);
		}
	}

	@Transactional(rollbackFor = DatabaseException.class)
	@Override
	public Optional<Trainer> updateTrainer(Trainer trainer) {
		log.debug("Updating trainer: {}", trainer);
		try {
			Optional<TrainerEntity> foundTrainer = trainerJpaRepository
					.findByUserIdUsername(trainer.getUsername());
			
			foundTrainer.get().getUserId().setFirstName(trainer.getFirstName());
			foundTrainer.get().getUserId().setLastName(trainer.getLastName());
			foundTrainer.get().getUserId().setIsActive(trainer.getIsActive());
			
			if (!passwordEncoder.matches(trainer.getPassword(), foundTrainer.get().getUserId().getPassword())) {
				foundTrainer.get().getUserId().setPassword(passwordEncoder.encode(trainer.getPassword()));
			}
			
			TrainerEntity updatedTrainer = trainerJpaRepository.save(foundTrainer.get());
			log.debug("Trainer with ID '{}' updated successfully.", updatedTrainer.getTrainerId());
			return Optional.of(updatedTrainer.toDomain());
		} catch (Exception e) {
			log.debug("Error while trying to update a Trainer.", e);
			throw new DatabaseException("Error while trying to update a Trainer.", e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<Trainer> getAllNonAssociatedTrainers(String username) {
		try {
			List<TrainerEntity> foundTrainers = trainerJpaRepository.findAllNonAssociated(username);
			if (!foundTrainers.isEmpty()) {
				return foundTrainers.stream().map(TrainerEntity::toDomain).collect(Collectors.toList());			
			} else {
				return Collections.emptyList();
			}			
		} catch (Exception e) {
			log.debug("Error while trying to fetch all Trainers from the database.", e);
			throw new DatabaseException("Error while trying to fetch all Trainers from the database.", e);
		}
	}
	
	@Transactional(readOnly = true)
	@Override
	public Optional<Trainer> findByUsernameAndPassword(String username, String password) {
		try {
			log.debug("Validating password for trainer with username: {}.", username);
			Optional<TrainerEntity> foundTrainer = trainerJpaRepository.findByUserIdUsername(username);
			if (foundTrainer.isPresent() && passwordEncoder.matches(password, foundTrainer.get().getUserId().getPassword())) {
				log.debug("Password is correct for trainer with username: {}.", username);
				return Optional.of(foundTrainer.get().toDomain());
			} else {
				log.debug("Incorrect password for trainer with username: {}.", username);
				return Optional.empty();
			}
		} catch (Exception e) {
			log.debug("Error while trying to validate a password for trainer with username: " + username, e);
			throw new DatabaseException("Error while trying to validate a password for trainer with username: " + username, e);
		}
	}

}
