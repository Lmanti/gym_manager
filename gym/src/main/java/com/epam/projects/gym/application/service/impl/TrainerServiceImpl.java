package com.epam.projects.gym.application.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.epam.projects.gym.application.dto.TrainerAssignedDto;
import com.epam.projects.gym.application.dto.request.ChangeUserStatus;
import com.epam.projects.gym.application.dto.request.TrainerRegister;
import com.epam.projects.gym.application.dto.request.TrainerUpdate;
import com.epam.projects.gym.application.dto.response.TrainerProfile;
import com.epam.projects.gym.application.dto.response.TrainerUpdated;
import com.epam.projects.gym.application.dto.response.UserCreated;
import com.epam.projects.gym.application.mapper.TrainerMapper;
import com.epam.projects.gym.application.service.TrainerService;
import com.epam.projects.gym.domain.entity.Trainer;
import com.epam.projects.gym.domain.entity.TrainingType;
import com.epam.projects.gym.domain.exception.CreationException;
import com.epam.projects.gym.domain.exception.NotFoundException;
import com.epam.projects.gym.domain.exception.UpdateException;
import com.epam.projects.gym.domain.repository.TrainerRepository;
import com.epam.projects.gym.domain.repository.TrainingTypeRepository;
import com.epam.projects.gym.domain.utils.Randomizer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TrainerServiceImpl implements TrainerService {
	
	private TrainerRepository trainerRepository;
	
	private TrainingTypeRepository trainingTypeRepository;
	
	public TrainerServiceImpl(
			TrainerRepository trainerRepository,
			TrainingTypeRepository trainingTypeRepository) {
		this.trainerRepository = trainerRepository;
		this.trainingTypeRepository = trainingTypeRepository;
	}

	@Override
	public List<TrainerProfile> getAllTrainers() {
		log.info("Fetching all trainers data.");
		List<Trainer> trainers = trainerRepository.getAllTrainers();
		log.info("Trainers data fetched successfully.");
		return trainers.stream().map(TrainerMapper::toProfile).collect(Collectors.toList());
	}

	@Override
	public Optional<UserCreated> createTrainer(TrainerRegister trainer) {
		log.info("Attempting to create a new trainer.");
		String randomUsername = null;
		boolean exist = true;
		
		if (trainer instanceof TrainerUpdate) {
			randomUsername = ((TrainerUpdate) trainer).getUsername();
		} else {
			randomUsername = 
					Randomizer.createUsername(trainer.getFirstName(), trainer.getLastName());				
		}
		
		exist = trainerRepository.findByUsername(randomUsername).isPresent();
		
		if (exist) {
			log.info("Trainer with username {} alredy exist, giving a new username and trying again.", randomUsername);
			return createTrainer(
					trainer.getSettingUsername(
						Randomizer.createUsername(trainer.getFirstName(), trainer.getLastName())
						+ Randomizer.getSerialNumber()));
		} else {
			log.info("Verifing if specialization with name {} exist.", trainer.getSpecialization().getLabel());
			Optional<TrainingType> specialization = trainingTypeRepository
					.findByName(trainer.getSpecialization().getLabel());
			
			if (specialization.isPresent()) {
				log.info("Creating new trainer: {}.", trainer);
				String newPassword = Randomizer.createPasword(trainer.getFirstName(), trainer.getLastName());
				Trainer newTrainer = new Trainer(
						trainer.getFirstName(),
						trainer.getLastName(),
						randomUsername,
						newPassword,
						true,
						specialization.get());
				
				Optional<Trainer> createdTrainer = trainerRepository.createTrainer(newTrainer);
				
				if (createdTrainer.isPresent()) {
					log.info("Trainer created successfuly with ID: {}.", createdTrainer.get().getId());
					
					UserCreated response = new UserCreated();
					response.setUsername(createdTrainer.get().getUsername());
					response.setPassword(newPassword);
					
					return Optional.of(response);
				} else {
					log.error("Error while trying to create a new Trainer.");
					throw new CreationException("Error while trying to create a new Trainer.");
				}
			} else {
				log.error("Couldn't find a training type with name: {}", trainer.getSpecialization().getLabel());
				throw new NotFoundException("Couldn't find a training type with name: " + trainer.getSpecialization().getLabel());
			}	
		}
	}

	@Override
	public Optional<TrainerUpdated> updateTrainer(TrainerUpdate update) {
		log.info("Attempting to update a trainer with username: {}.", update.getUsername());
		Optional<Trainer> foundTrainer = trainerRepository.findByUsername(update.getUsername());
		if (foundTrainer.isPresent()) {
			log.info("Updating trainer data, merging: {}.", foundTrainer.get());
			log.info("with: {}.", update);
			Trainer trainer = foundTrainer.get();
			
			trainer.setFirstName(update.getFirstName());
			trainer.setLastName(update.getLastName());
			trainer.setIsActive(update.isActive());
			
			Optional<Trainer> updatedTrainer = trainerRepository.updateTrainer(trainer);
			
			if (updatedTrainer.isPresent()) {
				log.info("Trainer updated successfully: {}.", updatedTrainer.get());
				return Optional.of(TrainerMapper.toUpdated(updatedTrainer.get()));					
			} else {
				log.error("Error while trying tu update trainer with username: {}", update.getUsername());
				throw new UpdateException("Error while trying tu update trainer with username: " + update.getUsername());					
			}
		} else {
			throw new NotFoundException("Couldn't find a trainer with username: " + update.getUsername());
		}
	}

	@Override
	public Optional<TrainerProfile> getTrainerByUsername(String username) {
		log.info("Looking for trainer with username: {}.", username);
		Optional<Trainer> foundTrainer = trainerRepository.findByUsername(username);
		if (foundTrainer.isPresent()) {
			log.info("Trainer with username {} found successfully.", username);
			return Optional.of(TrainerMapper.toProfile(foundTrainer.get()));			
		} else {
			log.error("Couldn't find a trainer with username: " + username);
			throw new NotFoundException("Couldn't find a trainer with username: " + username);
		}
	}

	@Override
	public boolean changeTrainerPassword(String username, String oldPassword, String newPassword) {
		log.info("Trying to update password for trainer with username: {}", username);
		Optional<Trainer> foundTrainer = trainerRepository.findByUsernameAndPassword(username, oldPassword);
		if (foundTrainer.isPresent()) {
			log.info("Matched. Updating password for trainer with username: {}", username);
			Trainer trainer = foundTrainer.get();
			trainer.setPassword(newPassword);
			trainerRepository.updateTrainer(trainer);
			log.info("Password updated succesfully.");
			return true;				
		} else {
			log.error("Invalid credentials, incorrect username or password.");
			throw new NotFoundException("Invalid credentials, incorrect username or password.");
		}
	}

	@Override
	public List<TrainerAssignedDto> getAllNonAssociatedTrainers(String username) {
		log.info("Fetching all non-associated trainers data.");
		List<Trainer> trainers = trainerRepository.getAllNonAssociatedTrainers(username);
		log.info("Non-Associated trainers data fetched successfully.");
		return trainers.stream().map(TrainerMapper::toAssignedDto).collect(Collectors.toList());
	}

	@Override
	public boolean changeTrainerStatus(ChangeUserStatus request) {
		log.info("Changing status of trainer with username: {}", request.getUsername());
		Optional<Trainer> foundTrainer = trainerRepository.findByUsername(request.getUsername());
		if (foundTrainer.isPresent()) {
			Trainer trainer = foundTrainer.get();
			trainer.setIsActive(request.isActive());
			trainerRepository.updateTrainer(trainer);
			log.info("Status changed successfully.");
			return true;
		} else {
			log.error("Couldn't find a trainer with username: {}", request.getUsername());
			throw new NotFoundException("Couldn't find a trainer with username: " + request.getUsername());
		}
	}

}
