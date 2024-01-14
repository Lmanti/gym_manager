package com.epam.projects.gym.application.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.epam.projects.gym.application.dto.request.TraineeRegister;
import com.epam.projects.gym.application.dto.request.TraineeUpdate;
import com.epam.projects.gym.application.dto.response.TraineeProfile;
import com.epam.projects.gym.application.dto.response.TraineeUpdated;
import com.epam.projects.gym.application.dto.response.UserCreated;
import com.epam.projects.gym.application.mapper.TraineeMapper;
import com.epam.projects.gym.application.service.TraineeService;
import com.epam.projects.gym.domain.entity.Trainee;
import com.epam.projects.gym.domain.repository.TraineeRepository;
import com.epam.projects.gym.domain.utils.Randomizer;
import com.epam.projects.gym.infrastructure.exception.NotFoundException;
import com.epam.projects.gym.infrastructure.exception.NotMatchException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TraineeServiceImpl implements TraineeService {
	
	private TraineeRepository traineeRepository;
	
	public TraineeServiceImpl(TraineeRepository traineeRepository) {
		this.traineeRepository = traineeRepository;
	}

	@Override
	public List<TraineeProfile> getAllTrainees() {
		List<Trainee> trainees = traineeRepository.getAllTrainees();
		return trainees.stream().map(TraineeMapper::toProfile).collect(Collectors.toList());
	}

	@Override
	public Optional<TraineeProfile> getTraineeByUsername(String username) {
		try {
			Optional<Trainee> foundTrainee = traineeRepository.findByUsername(username);
			if (foundTrainee.isPresent()) {
				return Optional.of(TraineeMapper.toProfile(foundTrainee.get()));			
			} else {
				throw new NotFoundException("Couldn't find a trainee with username: " + username);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return Optional.empty();
		}
	}

	@Override
	public Optional<UserCreated> createTrainee(TraineeRegister trainee) {
		try {
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
				return createTrainee(
						trainee.getSettingUsername(
							Randomizer.createUsername(trainee.getFirstName(), trainee.getLastName())
							+ Randomizer.getSerialNumber()));
			} else {
				Trainee newTrainee = new Trainee(
						trainee.getFirstName(),
						trainee.getLastName(),
						randomUsername,
						Randomizer.createPasword(trainee.getFirstName(), trainee.getLastName()),
						true,
						trainee.getDateOfBirth(),
						trainee.getAddress());
				
				Trainee createdTrainee = traineeRepository.createTrainee(newTrainee);
		
				UserCreated response = new UserCreated();
				response.setUsername(createdTrainee.getUsername());
				response.setPassword(createdTrainee.getPassword());
				
				return Optional.of(response);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return Optional.empty();
		}
	}

	@Override
	public Optional<TraineeUpdated> updateTrainee(TraineeUpdate update) {
		try {
			Optional<Trainee> foundTrainee = traineeRepository.findByUsername(update.getUsername());
			if (foundTrainee.isPresent()) {
				Trainee trainee = foundTrainee.get();
				
				trainee.setFirstName(update.getFirstName());
				trainee.setLastName(update.getLastName());
				trainee.setDateOfBirth(update.getDateOfBirth());
				trainee.setAddress(update.getAddress());
				trainee.setIsActive(update.isActive());
				
				Trainee updatedTrainee = traineeRepository.updateTrainee(trainee);
				
				return Optional.of(TraineeMapper.toUpdated(updatedTrainee));
			} else {
				throw new NotFoundException("Couldn't find a trainee with username: " + update.getUsername());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return Optional.empty();
		}
	}
	
	@Override
	public boolean deleteTrainee(String username) {
		try {
			Optional<Trainee> foundTrainee = traineeRepository.findByUsername(username);
			if (foundTrainee.isPresent()) {
				boolean deleted = traineeRepository.deleteTrainee(username);
				return deleted;
			} else {
				throw new NotFoundException("Couldn't find a trainee with username: " + username);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	@Override
	public boolean loginTrainee(String username, String password) {
		try {
			Optional<Trainee> foundTrainee = traineeRepository.findByUsername(username);
			if (foundTrainee.isPresent()) {
				boolean validation = foundTrainee.get().getPassword().equals(password);
				return validation;
			} else {
				throw new NotFoundException("Couldn't find a trainee with username: " + username);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean changeTraineePassword(String username, String oldPasword, String newPasword) {
		try {
			Optional<Trainee> foundTrainee = traineeRepository.findByUsername(username);
			if (foundTrainee.isPresent()) {
				Trainee trainee = foundTrainee.get();
				
				if (trainee.getPassword().equals(oldPasword)) {
					trainee.setPassword(newPasword);
					traineeRepository.updateTrainee(trainee);
					return true;					
				} else {
					throw new NotMatchException("Invalid Password.");
				}				
			} else {
				throw new NotFoundException("Couldn't find a trainee with username: " + username);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
}
