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
		try {
			UserEntity newUser = new UserEntity(
					null,
					newTrainee.getUserId().getFirstName(),
					newTrainee.getUserId().getLastName(),
					newTrainee.getUserId().getUsername(),
					newTrainee.getUserId().getPassword(),
					newTrainee.getUserId().isActive(),
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
	public Trainee findByUsername(String username) {
		try {
			Optional<TraineeEntity> foundTrainee = traineeJpaRepository.findByUserIdUsername(username);
			if (foundTrainee.isPresent()) {
				return foundTrainee.get().toDomain();
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error("Error while trying to fetch by username an users from the database.", e);
			throw new DatabaseException("Error while trying to fetch by username an users from the database.", e);
		}
	}

	@Transactional(rollbackFor = DatabaseException.class)
	@Override
	public Trainee updateTrainee(Trainee trainee) {
		try {
			Optional<TraineeEntity> foundTrainee = traineeJpaRepository
					.findByUserIdUsername(trainee.getUserId().getUsername());
			
			foundTrainee.get().getUserId().setFirstName(trainee.getUserId().getFirstName());
			foundTrainee.get().getUserId().setLastName(trainee.getUserId().getLastName());
			foundTrainee.get().getUserId().setUsername(trainee.getUserId().getUsername());
			foundTrainee.get().getUserId().setPassword(trainee.getUserId().getPassword());
			foundTrainee.get().getUserId().setIsActive(trainee.getUserId().isActive());
			foundTrainee.get().setAddress(trainee.getAddress());
			foundTrainee.get().setDateOfBirth(trainee.getDateOfBirth());
			
			TraineeEntity updatedTrainee = traineeJpaRepository.save(foundTrainee.get());
			return updatedTrainee.toDomain();
		} catch (Exception e) {
			log.error("Error while trying to update an user.", e);
			throw new DatabaseException("Error while trying to update an user.", e);
		}
	}

	@Transactional(rollbackFor = DatabaseException.class)
	@Override
	public boolean deleteTrainee(String username) {
		try {
			traineeJpaRepository.deleteByUserIdUsername(username);
			return true;
		} catch (Exception e) {
			log.error("Error while trying to delete an user.", e);
			throw new DatabaseException("Error while trying to delete an user.", e);
		}
	}

}
