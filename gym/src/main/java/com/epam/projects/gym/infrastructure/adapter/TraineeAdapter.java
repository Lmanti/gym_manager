package com.epam.projects.gym.infrastructure.adapter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

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
			log.error("Error while trying to register an user.", e);
			throw new DatabaseException("Error while trying to register an user.", e);
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
			log.error("Error while trying to fetch all users from the database.", e);
			throw new DatabaseException("Error while trying to fetch all users from the database.", e);
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

}
