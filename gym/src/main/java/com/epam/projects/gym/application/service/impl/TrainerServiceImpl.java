package com.epam.projects.gym.application.service.impl;

import java.util.List;
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
import com.epam.projects.gym.domain.entity.User;
import com.epam.projects.gym.domain.repository.TrainerRepository;
import com.epam.projects.gym.domain.repository.TrainingTypeRepository;
import com.epam.projects.gym.domain.utils.Randomizer;
import com.epam.projects.gym.infrastructure.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TrainerServiceImpl implements TrainerService {
	
	private TrainerRepository trainerRepository;
	
	private TrainingTypeRepository trainingTypeRepository;
	
	public TrainerServiceImpl(TrainerRepository trainerRepository,
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
	public UserCreated createTrainer(TrainerRegister trainer) {
		try {
			boolean exist = trainerRepository.findByUsername(
					Randomizer.createUsername(trainer.getFirstName(), trainer.getLastName())) != null;
			
			TrainingType specialization = trainingTypeRepository
					.findByName(trainer.getSpecialization().getLabel());
			
			User newUser = new User(
					null,
					trainer.getFirstName(),
					trainer.getLastName(),
					exist ? Randomizer.createUsername(trainer.getFirstName(), trainer.getLastName())
								.concat(Randomizer.getSerialNumber())
						: Randomizer.createUsername(trainer.getFirstName(), trainer.getLastName()),
					Randomizer.createPasword(trainer.getFirstName(), trainer.getLastName()),
					true,
					null,
					null);
			
			Trainer newTrainer = new Trainer(
					null,
					specialization,
					newUser,
					null);
			
			Trainer createdTrainer = trainerRepository.createTrainer(newTrainer);
	
			UserCreated response = new UserCreated();
			response.setUsername(createdTrainer.getUserId().getUsername());
			response.setPassword(createdTrainer.getUserId().getPassword());
			
			return response;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public TrainerUpdated updateTrainer(TrainerUpdate trainer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrainerProfile getTrainerByUsername(String username) {
		try {
			Trainer foundTrainer = trainerRepository.findByUsername(username);
			if (foundTrainer != null) {
				return TrainerMapper.toProfile(foundTrainer);			
			} else {
				throw new NotFoundException("Couldn't find a trainee with username: " + username);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public boolean changeTrainerPassword(String username, String oldPasword, String newPasword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loginTrainer(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
