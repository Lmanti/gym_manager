package com.epam.projects.gym.infrastructure.adapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.projects.gym.application.dto.request.TraineeTraining;
import com.epam.projects.gym.application.specification.TrainingSpecification;
import com.epam.projects.gym.domain.entity.Trainee;
import com.epam.projects.gym.domain.entity.Trainer;
import com.epam.projects.gym.domain.repository.TraineeRepository;
import com.epam.projects.gym.infrastructure.datasource.entity.TraineeEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainerEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainingEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.UserEntity;
import com.epam.projects.gym.infrastructure.datasource.postgresql.repository.TraineeJpaRepository;
import com.epam.projects.gym.infrastructure.datasource.postgresql.repository.TrainerJpaRepository;
import com.epam.projects.gym.infrastructure.datasource.postgresql.repository.TrainingJpaRepository;
import com.epam.projects.gym.infrastructure.exception.DatabaseException;
import com.epam.projects.gym.infrastructure.specification.TrainingEntitySpecification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TraineeAdapter implements TraineeRepository {

	private TraineeJpaRepository traineeJpaRepository;
	
	private TrainerJpaRepository trainerJpaRepository;
	
	private TrainingJpaRepository trainingJpaRepository;
	
	public TraineeAdapter(
			TraineeJpaRepository traineeJpaRepository,
			TrainerJpaRepository trainerJpaRepository,
			TrainingJpaRepository trainingJpaRepository
			) {
		this.traineeJpaRepository = traineeJpaRepository;
		this.trainerJpaRepository = trainerJpaRepository;
		this.trainingJpaRepository = trainingJpaRepository;
	}

	@Transactional(rollbackFor = DatabaseException.class)
	@Override
	public Trainee createTrainee(Trainee newTrainee) {
		log.info("Creating trainee: {}", newTrainee);
		try {
			UserEntity newUser = new UserEntity(
					newTrainee.getFirstName(),
					newTrainee.getLastName(),
					newTrainee.getUsername(),
					newTrainee.getPassword(),
					newTrainee.getIsActive());
			
			TraineeEntity trainee = new TraineeEntity(
					newTrainee.getDateOfBirth(),
					newTrainee.getAddress(),
					newUser);
			
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

	@Transactional(rollbackFor = DatabaseException.class)
	@Override
	public List<Trainer> assignTrainerBulk(TraineeTraining specification, List<String> trainerList) {
		log.info("Assigning trainers to trainee with username: {}", specification.getUsername());
		try {
			List<TrainingEntity> newTrainings = new ArrayList<>();
			List<TrainerEntity> foundTrainers = new ArrayList<>();
			Optional<TraineeEntity> foundTrainee = traineeJpaRepository
					.findByUserIdUsername(specification.getUsername());
			List<TrainingEntity> trainings = trainingJpaRepository
					.findAll(new TrainingEntitySpecification(new TrainingSpecification(specification)));
			if (!trainings.isEmpty()) {
				trainingJpaRepository.deleteAll(trainings);
			}
			trainerList.stream().forEach(trainerUsername -> {
				Optional<TrainerEntity> trainer = trainerJpaRepository.findByUserIdUsername(trainerUsername);
				TrainingEntity newTraining = new TrainingEntity(
						foundTrainee.get(),
						trainer.get(),
						foundTrainee.get().getUserId().getFirstName().concat("+")
							.concat(trainer.get().getSpecialization().getName())
							.concat("+").concat(trainerUsername),
						trainer.get().getSpecialization(),
						LocalDate.now(),
						1);
				foundTrainers.add(trainer.get());
				newTrainings.add(newTraining);
			});
			trainingJpaRepository.saveAll(newTrainings);
			return foundTrainers.stream()
					.map(TrainerEntity::toDomain).collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Error while trying to bulk assign trainers to a trainee.", e);
			throw new DatabaseException("Error while trying to bulk assign trainers to a trainee.", e);
		}
	}

}
