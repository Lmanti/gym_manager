package com.epam.projects.gym.infrastructure.adapter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.projects.gym.domain.entity.Trainee;
import com.epam.projects.gym.domain.repository.TraineeRepository;
import com.epam.projects.gym.infrastructure.datasource.entity.TraineeEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.UserEntity;
import com.epam.projects.gym.infrastructure.datasource.postgresql.repository.TraineeJpaRepository;
import com.epam.projects.gym.infrastructure.exception.DatabaseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TraineeAdapter implements TraineeRepository {

	private TraineeJpaRepository traineeJpaRepository;
	
	public TraineeAdapter(TraineeJpaRepository traineeJpaRepository) {
		this.traineeJpaRepository = traineeJpaRepository;
	}

	@Transactional(rollbackFor = DatabaseException.class)
	@Override
	public Trainee createTrainee(Trainee newTrainee) {
		log.info("Creating trainee: {}", newTrainee);
		try {
			UserEntity newUser = new UserEntity(
					null,
					newTrainee.getFirstName(),
					newTrainee.getLastName(),
					newTrainee.getUsername(),
					newTrainee.getPassword(),
					newTrainee.getIsActive(),
					null,
					null);
			
			TraineeEntity trainee = new TraineeEntity(
					null,
					newTrainee.getDateOfBirth(),
					newTrainee.getAddress(),
					newUser,
					null);
			
			newUser.setTraineeId(trainee);
			
			TraineeEntity createdTrainee = traineeJpaRepository.save(trainee);
			log.info("Trainee created successfully with ID: {}", createdTrainee.getTraineeId());
			return createdTrainee.toDomain();
		} catch (Exception e) {
			log.error("Error while trying to register a Trainee.", e);
			throw new DatabaseException("Error while trying to register a Trainee.", e);
		}
	}

	@Override
	public List<Trainee> getAllTrainees() {
		try {
			List<TraineeEntity> foundTrainees = traineeJpaRepository.findAll();
			if (!foundTrainees.isEmpty()) {
				return foundTrainees.stream().map(TraineeEntity::toDomain).collect(Collectors.toList());			
			} else {
				return Collections.emptyList();
			}			
		} catch (Exception e) {
			log.error("Error while trying to fetch all Trainees from the database.", e);
			throw new DatabaseException("Error while trying to fetch all Trainees from the database.", e);
		}
	}

	@Override
	public Optional<Trainee> findByUsername(String username) {
		try {
			Optional<TraineeEntity> foundTrainee = traineeJpaRepository.findByUserIdUsername(username);
			return foundTrainee.isPresent()
					? Optional.of(foundTrainee.get().toDomain())
					: Optional.empty();
		} catch (Exception e) {
			log.error("Error while trying to fetch by username an users from the database.", e);
			throw new DatabaseException("Error while trying to fetch by username an users from the database.", e);
		}
	}

	@Transactional(rollbackFor = DatabaseException.class)
	@Override
	public Trainee updateTrainee(Trainee trainee) {
		log.info("Updating trainee: {}", trainee);
		try {
			Optional<TraineeEntity> foundTrainee = traineeJpaRepository
					.findByUserIdUsername(trainee.getUsername());
			
			foundTrainee.get().getUserId().setFirstName(trainee.getFirstName());
			foundTrainee.get().getUserId().setLastName(trainee.getLastName());
			foundTrainee.get().getUserId().setPassword(trainee.getPassword());
			foundTrainee.get().getUserId().setIsActive(trainee.getIsActive());
			foundTrainee.get().setAddress(trainee.getAddress());
			foundTrainee.get().setDateOfBirth(trainee.getDateOfBirth());
			
			TraineeEntity updatedTrainee = traineeJpaRepository.save(foundTrainee.get());
			log.info("Trainee with ID '{}' updated successfully.", updatedTrainee.getTraineeId());
			return updatedTrainee.toDomain();
		} catch (Exception e) {
			log.error("Error while trying to update a Trainee.", e);
			throw new DatabaseException("Error while trying to update a Trainee.", e);
		}
	}

	@Transactional(rollbackFor = DatabaseException.class)
	@Override
	public boolean deleteTrainee(String username) {
		log.info("Deleting trainee with username: {}", username);
		try {
			traineeJpaRepository.deleteByUserIdUsername(username);
			log.info("Trainee with username '{}' deleted succesfully.", username);
			return true;
		} catch (Exception e) {
			log.error("Error while trying to delete an user.", e);
			throw new DatabaseException("Error while trying to delete an user.", e);
		}
	}

}
