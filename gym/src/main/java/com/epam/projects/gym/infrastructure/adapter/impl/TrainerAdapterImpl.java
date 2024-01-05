package com.epam.projects.gym.infrastructure.adapter.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.projects.gym.application.dto.UserDto;
import com.epam.projects.gym.application.dto.basics.TrainerBasicDto;
import com.epam.projects.gym.application.dto.requests.TrainerRegister;
import com.epam.projects.gym.application.dto.requests.TrainerUpdate;
import com.epam.projects.gym.infrastructure.adapter.TrainerAdapter;
import com.epam.projects.gym.infrastructure.datasource.entity.TraineeEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainerEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainingTypeEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.UserEntity;
import com.epam.projects.gym.infrastructure.datasource.repository.TrainerRepository;
import com.epam.projects.gym.infrastructure.datasource.repository.TrainingTypeRepository;
import com.epam.projects.gym.infrastructure.datasource.repository.UserRepository;
import com.epam.projects.gym.infrastructure.mappers.TrainerMapper;
import com.epam.projects.gym.infrastructure.mappers.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TrainerAdapterImpl implements TrainerAdapter {

	private UserRepository userDao;

	private TrainerRepository trainerDao;

	private TrainingTypeRepository trainingTypeDao;

	private UserMapper userMapper;

	private TrainerMapper trainerMapper;
	
	public TrainerAdapterImpl(UserRepository userDao, TrainerRepository trainerDao,
			TrainingTypeRepository trainingTypeDao) {
		this.userDao = userDao;
		this.trainerDao = trainerDao;
		this.trainingTypeDao = trainingTypeDao;
	}

	@Override
	public List<TrainerBasicDto> getAllTrainers() {
		try {
			List<TrainerEntity> trainees = trainerDao.findAll();
			return parseTrainersList(trainees);
		} catch (Exception e) {
			log.error("Error trying to retrieve all trainees info.", e);
			return Collections.emptyList();
		}
	}

	@Override
	public TrainerBasicDto getTrainerById(UUID id) {
		try {
			TrainerEntity trainer = trainerDao.findById(id);
			TrainerBasicDto dto = mapTrainerToDto(trainer);
			return dto;
		} catch (Exception e) {
			log.error("Error trying to retrieve trainer info with ID: " + id, e);
			return null;
		}
	}
	
	@Override
	public List<TrainerBasicDto> getAllByIds(List<UUID> trainers) {
		try {
			List<TrainerEntity> traineeList = trainerDao.findAll();
			return parseTrainersList(traineeList)
					.stream()
					.filter(x -> trainers.contains(x.getId()))
					.collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Error trying to retrieve all trainers info.", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public TrainerBasicDto getTrainerByUsername(String username) {
		try {
			UserEntity user = userDao.findByUsername(username);
			if (user != null) {
				TrainerEntity trainee = trainerDao.findByUserId(user.getId());
				return mapTrainerToDto(trainee);
			} else {
				log.error("There're no trainers with username: {}", username);
				return null;
			}			
		} catch (Exception e) {
			log.error("Error trying to retrieve the trainer info with username: " + username, e);
			return null;
		}
	}

	@Override
	public TrainerBasicDto createTrainer(TrainerRegister trainer) {
		try {
			UserDto dto = new UserDto(trainer.getFirstName(), trainer.getLastName());
			UserEntity newUser = userMapper.mapDtoToUser(dto);
			if (newUser != null) {
				TrainingTypeEntity tType = trainingTypeDao.findByName(trainer.getSpecialization().getLabel());
				if (tType != null) {
					if (userExist(newUser.getUsername())) {
						newUser.setUsername(newUser.getUsername().concat(userMapper.getSerialNumber()));
					}
					TrainerEntity newTrainer = new TrainerEntity(
							UUID.randomUUID(),
							new TrainingTypeEntity(tType.getTrainingTypeId(), null, null),
							newUser,
							Collections.emptyList()
							);
					userDao.save(newUser);
					trainerDao.save(newTrainer);
					return getTrainerById(newTrainer.getTrainerId());
				} else {
					log.error("Error trying to find the training type with name: {}", trainer.getSpecialization().getLabel());
					return null;
				}
			} else {
				log.error("Error trying to create a new user.");
				return null;
			}
		} catch (Exception e) {
			log.error("Error trying to create a new trainer.", e);
			throw e;
		}
	}

	@Override
	public TrainerBasicDto updateTrainer(TrainerUpdate trainer) {
		try {
			TrainerBasicDto original = getTrainerByUsername(trainer.getUsername());
			TrainerBasicDto updated = trainerMapper.parseUpdateToBasic(original, trainer);
			UserEntity user = userMapper.mapDtoToUser(updated);
			if (user != null) {
				TrainingTypeEntity tType = trainingTypeDao.findByName(original.getSpecialization());
				TrainerEntity newTrainer = new TrainerEntity(
						original.getTrainerId(),
						new TrainingTypeEntity(tType.getTrainingTypeId(), null, null),
						user,
						original.getTrainees().stream()
						.map(x -> new TraineeEntity(x, null, null, null, null))
						.collect(Collectors.toList())
						);
				userDao.save(user);
				trainerDao.save(newTrainer);
				return getTrainerById(newTrainer.getTrainerId());
			} else {
				log.error("Error trying to update an user.");
				return null;
			}
		} catch (Exception e) {
			log.error("Error trying to update a trainer.", e);
			return null;
		}
	}
	
	@Override
	public boolean changeTrainerPassword(String username, String newPasword) {
		try {
			UserEntity user = userDao.findByUsername(username);
			user.setPassword(newPasword);
			userDao.save(user);
			log.info("User password changed susscessfully for user: {}.", username);
			return true;
		} catch (Exception e) {
			log.error("Error trying to change the user pasword.", e);
			return false;
		}
	}
	
	/**
	 * Maps a trainer to a DTO.
	 * @param trainer
	 * 		- trainer to map.
	 * @return DTO of trainer.
	 */
	private TrainerBasicDto mapTrainerToDto(TrainerEntity trainer) {
		try {
			if (trainer != null) {
				TrainerBasicDto dto = new TrainerBasicDto();
				UserEntity user = userDao.findById(trainer.getUserId().getId());
				dto.setId(user.getId());
				dto.setFirstName(user.getFirstName());
				dto.setLastName(user.getLastName());
				dto.setUsername(user.getUsername());
				dto.setPassword(user.getPassword());
				dto.setActive(user.getIsActive());
				dto.setTrainerId(trainer.getTrainerId());
				dto.setSpecialization(trainingTypeDao.findById(trainer.getSpecialization().getTrainingTypeId()).getName());
				dto.setTrainees(trainer.getTrainees()
						.stream()
						.map(TraineeEntity::getTraineeId)
						.collect(Collectors.toList()));
				return dto;
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error("Error mapping trainer to DTO.", e);
			return null;
		}
	}

	private List<TrainerBasicDto> parseTrainersList(List<TrainerEntity> trainers) {
		try {
			List<TrainerBasicDto> dtoList = new ArrayList<>();
			trainers.stream().forEach(t -> {
				TrainerBasicDto dto = mapTrainerToDto(t);
				dtoList.add(dto);
			});
			return dtoList;
		} catch (Exception e) {
			log.error("Error parsing the trainers list to DTO.", e);
			return Collections.emptyList();
		}
	}
	
	private boolean userExist(String username) {
		UserEntity exist = userDao.findByUsername(username);
		return exist !=  null;
	}

	@Autowired
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Autowired
	public void setTrainerMapper(TrainerMapper trainerMapper) {
		this.trainerMapper = trainerMapper;
	}
	
}
