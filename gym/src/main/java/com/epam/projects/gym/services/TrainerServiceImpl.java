package com.epam.projects.gym.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.projects.gym.dto.UserDto;
import com.epam.projects.gym.dto.basics.TrainerBasicDto;
import com.epam.projects.gym.dto.requests.TrainerRegister;
import com.epam.projects.gym.dto.requests.TrainerUpdate;
import com.epam.projects.gym.mappers.TrainerMapper;
import com.epam.projects.gym.mappers.UserMapper;
import com.epam.projects.gym.models.Trainer;
import com.epam.projects.gym.models.TrainingType;
import com.epam.projects.gym.models.User;
import com.epam.projects.gym.repositories.TrainerDao;
import com.epam.projects.gym.repositories.TrainingTypeDao;
import com.epam.projects.gym.repositories.UserDao;

/**
 * Trainer service.
 * @author lherreram
 */
@Service
public class TrainerServiceImpl implements TrainerService {
	
	/**
	 * Logger for TrainerServiceImpl.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);

	/**
	 * User DAO.
	 */
	@Autowired
	private UserDao userDao;
	
	/**
	 * Trainer DAO.
	 */
	@Autowired
	private TrainerDao trainerDao;
	
	/**
	 * Training Type DAO.
	 */
	@Autowired
	private TrainingTypeDao trainingTypeDao;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private TrainerMapper trainerMapper;

	@Override
	public List<TrainerBasicDto> getAllTrainers() {
		try {
			List<Trainer> trainees = trainerDao.findAll();
			return parseTrainersList(trainees);
		} catch (Exception e) {
			logger.error("Error trying to retrieve all trainees info.", e);
			return Collections.emptyList();
		}
	}

	@Override
	public TrainerBasicDto getTrainerById(UUID id) {
		try {
			Trainer trainer = trainerDao.findById(id);
			TrainerBasicDto dto = mapTrainerToDto(trainer);
			return dto;
		} catch (Exception e) {
			logger.error("Error trying to retrieve trainer info with ID: " + id, e);
			return null;
		}
	}
	
	@Override
	public List<TrainerBasicDto> getAllByIds(List<UUID> trainers) {
		try {
			List<Trainer> traineeList = trainerDao.findAll();
			return parseTrainersList(traineeList)
					.stream()
					.filter(x -> trainers.contains(x.getId()))
					.collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Error trying to retrieve all trainers info.", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public TrainerBasicDto getTrainerByUsername(String username) {
		try {
			User user = userDao.findByUsername(username);
			if (user != null) {
				Trainer trainee = trainerDao.findByUserId(user.getId());
				return mapTrainerToDto(trainee);
			} else {
				logger.error("There're no trainers with username: {}", username);
				return null;
			}			
		} catch (Exception e) {
			logger.error("Error trying to retrieve the trainer info with username: " + username, e);
			return null;
		}
	}

	@Override
	public TrainerBasicDto createTrainer(TrainerRegister trainer) {
		try {
			UserDto dto = new UserDto(trainer.getFirstName(), trainer.getLastName());
			User newUser = userMapper.mapDtoToUser(dto);
			if (newUser != null) {
				TrainingType tType = trainingTypeDao.findByName(trainer.getSpecialization().getLabel());
				if (tType != null) {
					if (userExist(newUser.getUsername())) {
						newUser.setUsername(newUser.getUsername().concat(userMapper.getSerialNumber()));
					}
					Trainer newTrainer = new Trainer(
							newUser.getId(),
							tType.getId()
							);
					userDao.save(newUser);
					trainerDao.save(newTrainer);
					return getTrainerById(newTrainer.getId());
				} else {
					logger.error("Error trying to find the training type with name: {}", trainer.getSpecialization().getLabel());
					return null;
				}
			} else {
				logger.error("Error trying to create a new user.");
				return null;
			}
		} catch (Exception e) {
			logger.error("Error trying to create a new trainer.", e);
			throw e;
		}
	}

	@Override
	public TrainerBasicDto updateTrainer(TrainerUpdate trainer) {
		try {
			TrainerBasicDto original = getTrainerByUsername(trainer.getUsername());
			TrainerBasicDto updated = trainerMapper.parseUpdateToBasic(original, trainer);
			User user = userMapper.mapDtoToUser(updated);
			if (user != null) {
				TrainingType tType = trainingTypeDao.findByName(original.getSpecialization());
				Trainer newTrainer = new Trainer(
						original.getTrainerId(),
						user.getId(),
						tType.getId()
						);
				userDao.save(user);
				trainerDao.save(newTrainer);
				return getTrainerById(newTrainer.getId());
			} else {
				logger.error("Error trying to update an user.");
				return null;
			}
		} catch (Exception e) {
			logger.error("Error trying to update a trainer.", e);
			return null;
		}
	}
	
	@Override
	public boolean changeTrainerPassword(String username, String newPasword) {
		try {
			User user = userDao.findByUsername(username);
			user.setPassword(newPasword);
			userDao.save(user);
			logger.info("User password changed susscessfully for user: {}.", username);
			return true;
		} catch (Exception e) {
			logger.error("Error trying to change the user pasword.", e);
			return false;
		}
	}
	
	/**
	 * Maps a trainer to a DTO.
	 * @param trainer
	 * 		- trainer to map.
	 * @return DTO of trainer.
	 */
	private TrainerBasicDto mapTrainerToDto(Trainer trainer) {
		try {
			if (trainer != null) {
				TrainerBasicDto dto = new TrainerBasicDto();
				User user = userDao.findById(trainer.getUserId());
				dto.setId(user.getId());
				dto.setFirstName(user.getFirstName());
				dto.setLastName(user.getLastName());
				dto.setUsername(user.getUsername());
				dto.setPassword(user.getPassword());
				dto.setActive(user.isActive());
				dto.setTrainerId(trainer.getId());
				dto.setSpecialization(trainingTypeDao.findById(trainer.getSpecialization()).getName());
				dto.setTrainees(trainer.getTrainees());
				return dto;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Error mapping trainer to DTO.", e);
			return null;
		}
	}
	
	/**
	 * Map a list of trainers to a list of DTOs of trainer.
	 * @param trainers
	 * 		- List of trainers to map.
	 * @return List of DTOs of trainer.
	 */
	private List<TrainerBasicDto> parseTrainersList(List<Trainer> trainers) {
		try {
			List<TrainerBasicDto> dtoList = new ArrayList<>();
			trainers.stream().forEach(t -> {
				TrainerBasicDto dto = mapTrainerToDto(t);
				dtoList.add(dto);
			});
			return dtoList;
		} catch (Exception e) {
			logger.error("Error parsing the trainers list to DTO.", e);
			return Collections.emptyList();
		}
	}
	
	private boolean userExist(String username) {
		User exist = userDao.findByUsername(username);
		return exist !=  null;
	}
	
}
