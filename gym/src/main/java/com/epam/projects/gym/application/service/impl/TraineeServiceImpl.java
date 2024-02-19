package com.epam.projects.gym.application.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.epam.projects.gym.application.dto.TrainerAssignedDto;
import com.epam.projects.gym.application.dto.request.ChangeUserStatus;
import com.epam.projects.gym.application.dto.request.TraineeRegister;
import com.epam.projects.gym.application.dto.request.TraineeTraining;
import com.epam.projects.gym.application.dto.request.TraineeUpdate;
import com.epam.projects.gym.application.dto.request.UpdateTrainerList;
import com.epam.projects.gym.application.dto.response.TraineeProfile;
import com.epam.projects.gym.application.dto.response.TraineeUpdated;
import com.epam.projects.gym.application.dto.response.UserCreated;
import com.epam.projects.gym.application.mapper.TraineeMapper;
import com.epam.projects.gym.application.mapper.TrainerMapper;
import com.epam.projects.gym.application.service.TraineeService;
import com.epam.projects.gym.domain.entity.Trainee;
import com.epam.projects.gym.domain.exception.CreationException;
import com.epam.projects.gym.domain.exception.NotFoundException;
import com.epam.projects.gym.domain.exception.UpdateException;
import com.epam.projects.gym.domain.repository.TraineeRepository;
import com.epam.projects.gym.domain.repository.TrainerRepository;
import com.epam.projects.gym.domain.utils.Randomizer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TraineeServiceImpl implements TraineeService {
	
	private TraineeRepository traineeRepository;
	
	private TrainerRepository trainerRepository;
	
	public TraineeServiceImpl(TraineeRepository traineeRepository, TrainerRepository trainerRepository) {
		this.traineeRepository = traineeRepository;
		this.trainerRepository = trainerRepository;
	}

	@Override
	public List<TraineeProfile> getAllTrainees() {
		log.info("Fetching all trainees data.");
		List<Trainee> trainees = traineeRepository.getAllTrainees();
		log.info("Trainees data fetched successfully.");
		return trainees.stream().map(TraineeMapper::toProfile).collect(Collectors.toList());
	}

	@Override
	public Optional<TraineeProfile> getTraineeByUsername(String username) {
		log.info("Looking for trainee with username: {}.", username);
		Optional<Trainee> foundTrainee = traineeRepository.findByUsername(username);
		if (foundTrainee.isPresent()) {
			log.info("Trainee with username {} found successfully.", username);
			return Optional.of(TraineeMapper.toProfile(foundTrainee.get()));			
		} else {
			log.error("Couldn't find a trainee with username: " + username);
			throw new NotFoundException("Couldn't find a trainee with username: " + username);
		}
	}

	@Override
	public Optional<UserCreated> createTrainee(TraineeRegister trainee) {
		
		log.info("Attempting to create a new trainee.");
		String randomUsername = null;
		boolean exist = true;
		
		if (trainee instanceof TraineeUpdate) {
			randomUsername = ((TraineeUpdate) trainee).getUsername();
		} else {
			randomUsername = 
					Randomizer.createUsername(trainee.getFirstName(), trainee.getLastName());				
		}
		
		exist = traineeRepository.findByUsername(randomUsername).isPresent();
		
		if (exist) {
			log.info("Trainee with username {} alredy exist, giving a new username and trying again.", randomUsername);
			return createTrainee(
					trainee.getSettingUsername(
						Randomizer.createUsername(trainee.getFirstName(), trainee.getLastName())
						+ Randomizer.getSerialNumber()));
		} else {
			String newPassword = Randomizer.createPasword(trainee.getFirstName(), trainee.getLastName());
			log.info("Creating new trainee: {}.", trainee);
			Trainee newTrainee = new Trainee(
					trainee.getFirstName(),
					trainee.getLastName(),
					randomUsername,
					newPassword,
					true,
					trainee.getDateOfBirth(),
					trainee.getAddress());
			
			Optional<Trainee> createdTrainee = traineeRepository.createTrainee(newTrainee);
	
			if (createdTrainee.isPresent()) {
				log.info("Trainee created successfuly with ID: {}.", createdTrainee.get().getId());
				
				UserCreated response = new UserCreated();
				response.setUsername(createdTrainee.get().getUsername());
				response.setPassword(newPassword);
				
				return Optional.of(response);
			} else {
				log.error("Error while trying to create a new Trainee.");
				throw new CreationException("Error while trying to create a new Trainee.");
			}
			
			
		}
	}

	@Override
	public Optional<TraineeUpdated> updateTrainee(TraineeUpdate update) {
		
		log.info("Attempting to update a trainee with username: {}.", update.getUsername());
		Optional<Trainee> foundTrainee = traineeRepository.findByUsername(update.getUsername());
		if (foundTrainee.isPresent()) {
			log.info("Updating trainee data, merging: {}.", foundTrainee.get());
			log.info("with: {}.", update);
			
			Trainee trainee = foundTrainee.get();
			
			trainee.setFirstName(update.getFirstName());
			trainee.setLastName(update.getLastName());
			trainee.setDateOfBirth(update.getDateOfBirth());
			trainee.setAddress(update.getAddress());
			trainee.setIsActive(update.isActive());
			
			Optional<Trainee> updatedTrainee = traineeRepository.updateTrainee(trainee);
			
			if (updatedTrainee.isPresent()) {
				log.info("Trainee updated successfully: {}.", updatedTrainee.get());
				return Optional.of(TraineeMapper.toUpdated(updatedTrainee.get()));					
			} else {
				log.error("Error while trying tu update trainee with username: {}", update.getUsername());
				throw new UpdateException("Error while trying tu update trainee with username: " + update.getUsername());					
			}
			
		} else {
			log.error("Couldn't find a trainee with username: {}", update.getUsername());
			throw new NotFoundException("Couldn't find a trainee with username: " + update.getUsername());
		}
	}
	
	@Override
	public boolean deleteTrainee(String username) {
		log.info("Deleting trainee with username: {}", username);
		Optional<Trainee> foundTrainee = traineeRepository.findByUsername(username);
		if (foundTrainee.isPresent()) {
			boolean deleted = traineeRepository.deleteTrainee(username);
			log.info("Trainee with username {} deleted successfully.", username);
			return deleted;
		} else {
			log.error("Couldn't find a trainee with username: ", username);
			throw new NotFoundException("Couldn't find a trainee with username: " + username);
		}
	}

	@Override
	public boolean changeTraineePassword(String username, String oldPasword, String newPasword) {
		log.info("Trying to update password for trainee with username: {}", username);
		Optional<Trainee> foundTrainee = traineeRepository.findByUsernameAndPassword(username, oldPasword);
		if (foundTrainee.isPresent()) {
			log.info("Matched. Updating password for trainee with username: {}", username);
			Trainee trainee = foundTrainee.get();
			trainee.setPassword(newPasword);
			traineeRepository.updateTrainee(trainee);
			log.info("Password updated succesfully.");
			return true;				
		} else {
			log.error("Invalid credentials, incorrect username or password.");
			throw new NotFoundException("Invalid credentials, incorrect username or password.");
		}
	}

	@Override
	public List<TrainerAssignedDto> updateTrainerList(UpdateTrainerList newData) {
		log.info("Updating trainee's trainer list for trainee with username: {}", newData.getUsername());
		Optional<Trainee> foundTrainee = traineeRepository.findByUsername(newData.getUsername());
		if (foundTrainee.isPresent()) {
			newData.getTrainerList().stream().forEach(trainerUsername -> {
				log.info("Validating if trainer with username {} exist.", trainerUsername);
				if (!trainerRepository.findByUsername(trainerUsername).isPresent()) {
					throw new NotFoundException("Couldn't find a trainer with username: " + trainerUsername);
				}
			});
			TraineeTraining specification = new TraineeTraining();
			specification.setUsername(newData.getUsername());
			log.info("Assigning trainers to trainee's list.");
			return traineeRepository.assignTrainerBulk(specification, newData.getTrainerList())
					.stream()
					.map(TrainerMapper::toAssignedDto)
					.collect(Collectors.toList());
		} else {
			log.error("Couldn't find a trainee with username: ", newData.getUsername());
			throw new NotFoundException("Couldn't find a trainee with username: " + newData.getUsername());
		}
	}

	@Override
	public boolean changeTraineeStatus(ChangeUserStatus request) {
		log.info("Changing status of trainee with username: {}", request.getUsername());
		Optional<Trainee> foundTrainee = traineeRepository.findByUsername(request.getUsername());
		if (foundTrainee.isPresent()) {
			Trainee trainee = foundTrainee.get();
			trainee.setIsActive(request.isActive());
			traineeRepository.updateTrainee(trainee);
			log.info("Status changed successfully.");
			return true;
		} else {
			log.error("Couldn't find a trainee with username: {}", request.getUsername());
			throw new NotFoundException("Couldn't find a trainee with username: " + request.getUsername());
		}
	}
	
}
