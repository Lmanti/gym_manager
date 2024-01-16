package com.epam.projects.gym.application.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.epam.projects.gym.application.dto.request.TrainerRegister;
import com.epam.projects.gym.application.dto.request.TrainerUpdate;
import com.epam.projects.gym.application.dto.response.TrainerProfile;
import com.epam.projects.gym.application.dto.response.TrainerUpdated;
import com.epam.projects.gym.application.dto.response.UserCreated;
import com.epam.projects.gym.application.mapper.TrainerMapper;
import com.epam.projects.gym.application.service.TrainerService;
import com.epam.projects.gym.domain.entity.Trainer;
import com.epam.projects.gym.domain.entity.TrainingType;
import com.epam.projects.gym.domain.repository.TrainerRepository;
import com.epam.projects.gym.domain.repository.TrainingTypeRepository;
import com.epam.projects.gym.domain.utils.Randomizer;
import com.epam.projects.gym.infrastructure.exception.NotFoundException;
import com.epam.projects.gym.infrastructure.exception.NotMatchException;

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
		List<Trainer> trainers = trainerRepository.getAllTrainers();
		return trainers.stream().map(TrainerMapper::toProfile).collect(Collectors.toList());
	}

	@Override
	public Optional<UserCreated> createTrainer(TrainerRegister trainer) {
		try {
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
				return createTrainer(
						trainer.getSettingUsername(
							Randomizer.createUsername(trainer.getFirstName(), trainer.getLastName())
							+ Randomizer.getSerialNumber()));
			} else {
				Optional<TrainingType> specialization = trainingTypeRepository
						.findByName(trainer.getSpecialization().getLabel());
				
				if (specialization.isPresent()) {
					Trainer newTrainer = new Trainer(
							trainer.getFirstName(),
							trainer.getLastName(),
							randomUsername,
							Randomizer.createPasword(trainer.getFirstName(), trainer.getLastName()),
							true,
							specialization.get());
					
					Trainer createdTrainer = trainerRepository.createTrainer(newTrainer);
			
					UserCreated response = new UserCreated();
					response.setUsername(createdTrainer.getUsername());
					response.setPassword(createdTrainer.getPassword());
					
					return Optional.of(response);
				} else {
					throw new NotFoundException("Couldn't find a training type with name: " + trainer.getSpecialization().getLabel());
				}	
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return Optional.empty();
		}
	}

	@Override
	public Optional<TrainerUpdated> updateTrainer(TrainerUpdate update) {
		try {
			Optional<Trainer> foundTrainer = trainerRepository.findByUsername(update.getUsername());
			if (foundTrainer.isPresent()) {
				Trainer trainer = foundTrainer.get();
				
				trainer.setFirstName(update.getFirstName());
				trainer.setLastName(update.getLastName());
				trainer.setIsActive(update.isActive());
				
				Trainer updatedTrainer = trainerRepository.updateTrainer(trainer);
				
				return Optional.of(TrainerMapper.toUpdated(updatedTrainer));
			} else {
				throw new NotFoundException("Couldn't find a trainee with username: " + update.getUsername());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return Optional.empty();
		}
	}

	@Override
	public Optional<TrainerProfile> getTrainerByUsername(String username) {
		try {
			Optional<Trainer> foundTrainer = trainerRepository.findByUsername(username);
			if (foundTrainer.isPresent()) {
				return Optional.of(TrainerMapper.toProfile(foundTrainer.get()));			
			} else {
				throw new NotFoundException("Couldn't find a trainee with username: " + username);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return Optional.empty();
		}
	}
	
	@Override
	public boolean loginTrainer(String username, String password) {
		try {
			Optional<Trainer> foundTrainer = trainerRepository.findByUsername(username);
			if (foundTrainer.isPresent()) {
				boolean validation = foundTrainer.get().getPassword().equals(password);
				return validation;
			} else {
				throw new NotFoundException("Couldn't find a trainer with username: " + username);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean changeTrainerPassword(String username, String oldPassword, String newPassword) {
		try {
			Optional<Trainer> foundTrainer = trainerRepository.findByUsername(username);
			if (foundTrainer.isPresent()) {
				Trainer trainer = foundTrainer.get();
				
				if (trainer.getPassword().equals(oldPassword)) {
					trainer.setPassword(newPassword);
					trainerRepository.updateTrainer(trainer);
					return true;					
				} else {
					throw new NotMatchException("Invalid Password.");
				}				
			} else {
				throw new NotFoundException("Couldn't find a trainer with username: " + username);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

}
