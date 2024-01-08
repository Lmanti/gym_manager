package com.epam.projects.gym.application.service.impl;

import java.util.List;
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
import com.epam.projects.gym.domain.entity.User;
import com.epam.projects.gym.domain.repository.TraineeRepository;
import com.epam.projects.gym.domain.utils.Randomizer;
import com.epam.projects.gym.infrastructure.exception.NotFoundException;

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
	public TraineeProfile getTraineeByUsername(String username) {
		try {
			Trainee foundTrainee = traineeRepository.findByUsername(username);
			if (foundTrainee != null) {
				return TraineeMapper.toProfile(foundTrainee);			
			} else {
				throw new NotFoundException("Couldn't find a trainee with username: " + username);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public UserCreated createTrainee(TraineeRegister trainee) {
		try {
			boolean exist = traineeRepository.findByUsername(
					Randomizer.createUsername(trainee.getFirstName(), trainee.getLastName())) != null;
			
			User newUser = new User(
					null,
					trainee.getFirstName(),
					trainee.getLastName(),
					exist ? Randomizer.createUsername(trainee.getFirstName(), trainee.getLastName())
								.concat(Randomizer.getSerialNumber())
						: Randomizer.createUsername(trainee.getFirstName(), trainee.getLastName()),
					Randomizer.createPasword(trainee.getFirstName(), trainee.getLastName()),
					true,
					null,
					null);
			
			Trainee newTrainee = new Trainee(
					null,
					trainee.getDateOfBirth(),
					trainee.getAddress(),
					newUser,
					null);
			
			Trainee createdTrainee = traineeRepository.createTrainee(newTrainee);
	
			UserCreated response = new UserCreated();
			response.setUsername(createdTrainee.getUserId().getUsername());
			response.setPassword(createdTrainee.getUserId().getPassword());
			
			return response;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public TraineeUpdated updateTrainee(TraineeUpdate update) {
		try {
			Trainee foundTrainee = traineeRepository.findByUsername(update.getUsername());
			if (foundTrainee != null) {
				
				User user = new User(
						foundTrainee.getUserId().getId(),
						update.getFirstName(),
						update.getLastName(),
						update.getUsername(),
						foundTrainee.getUserId().getPassword(),
						update.isActive(),
						null,
						null);
				
				Trainee trainee = new Trainee(
						foundTrainee.getId(),
						update.getDateOfBirth(),
						update.getAddress(),
						user,
						foundTrainee.getTrainers());
				
				Trainee updatedTrainee = traineeRepository.updateTrainee(trainee);
				
				return TraineeMapper.toUpdated(updatedTrainee);
			} else {
				throw new NotFoundException("Couldn't find a trainee with username: " + update.getUsername());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public boolean deleteTrainee(String username) {
		try {
			Trainee foundTrainee = traineeRepository.findByUsername(username);
			if (foundTrainee != null) {
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
	public boolean loginTrainee(String username, String pasword) {
		try {
			Trainee foundTrainee = traineeRepository.findByUsername(username);
			if (foundTrainee != null) {
				boolean validation = foundTrainee.getUserId().getPassword().equals(pasword);
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
			Trainee foundTrainee = traineeRepository.findByUsername(username);
			if (foundTrainee != null) {
				User user = new User(
						foundTrainee.getUserId().getId(),
						foundTrainee.getUserId().getFirstName(),
						foundTrainee.getUserId().getLastName(),
						foundTrainee.getUserId().getUsername(),
						newPasword,
						foundTrainee.getUserId().isActive(),
						null,
						null);
				
				Trainee trainee = new Trainee(
						foundTrainee.getId(),
						foundTrainee.getDateOfBirth(),
						foundTrainee.getAddress(),
						user,
						foundTrainee.getTrainers());
				
				traineeRepository.updateTrainee(trainee);
				
				return true;
			} else {
				throw new NotFoundException("Couldn't find a trainee with username: " + username);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
}
