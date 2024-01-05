package com.epam.projects.gym.infrastructure.adapter.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.projects.gym.application.dto.basics.TraineeBasicDto;
import com.epam.projects.gym.application.dto.requests.TraineeRegister;
import com.epam.projects.gym.application.dto.requests.TraineeUpdate;
import com.epam.projects.gym.infrastructure.adapter.TraineeAdapter;
import com.epam.projects.gym.infrastructure.datasource.entity.TraineeEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainerEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.UserEntity;
import com.epam.projects.gym.infrastructure.datasource.repository.TraineeRepository;
import com.epam.projects.gym.infrastructure.datasource.repository.UserRepository;
import com.epam.projects.gym.infrastructure.mappers.TraineeMapper;
import com.epam.projects.gym.infrastructure.mappers.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TraineeAdapterImpl implements TraineeAdapter {

	private UserRepository userDao;

	private TraineeRepository traineeDao;

	private UserMapper userMapper;

	private TraineeMapper traineeMapper;
	
	public TraineeAdapterImpl(UserRepository userDao, TraineeRepository traineeDao) {
		this.userDao = userDao;
		this.traineeDao = traineeDao;
	}

	@Override
	public List<TraineeBasicDto> getAllTrainees() {
		try {
			List<TraineeEntity> trainees = traineeDao.findAll();
			return parseTraineesList(trainees);
		} catch (Exception e) {
			log.error("Error trying to retrieve all trainees info.", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public TraineeBasicDto getTraineeById(UUID id) {
		try {
			TraineeEntity trainee = traineeDao.findById(id);
			return mapTraineeToDto(trainee);
		} catch (Exception e) {
			log.error("Error trying to retrieve all trainee info with ID: " + id, e);
			return null;
		}
	}
	
	@Override
	public List<TraineeBasicDto> getAllByIds(List<UUID> trainees) {
		try {
			List<TraineeEntity> traineeList = traineeDao.findAll();
			return parseTraineesList(traineeList)
					.stream()
					.filter(x -> trainees.contains(x.getId()))
					.collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Error trying to retrieve all trainees info.", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public TraineeBasicDto getTraineeByUsername(String username) {
		try {
			UserEntity user = userDao.findByUsername(username);
			if (user != null) {
				TraineeEntity trainee = traineeDao.findByUserId(user.getId());
				return mapTraineeToDto(trainee);
			} else {
				log.error("There're no trainees with username: {}", username);
				return null;
			}			
		} catch (Exception e) {
			log.error("Error trying to retrieve all trainee info with username: " + username, e);
			return null;
		}
	}

	@Override
	public TraineeBasicDto createTrainee(TraineeRegister trainee) {
		try {
			TraineeBasicDto dto = traineeMapper.mapRequestToBasic(trainee);
			UserEntity newUser = userMapper.mapDtoToUser(dto);
			if (newUser != null) {
				if (userExist(newUser.getUsername())) {
					newUser.setUsername(newUser.getUsername().concat(userMapper.getSerialNumber()));
				}
				TraineeEntity newTrainee = new TraineeEntity(
						UUID.randomUUID(),
						dto.getDateOfBirth(),
						dto.getAddress(),
						newUser,
						Collections.emptyList()
						);
				userDao.save(newUser);
				traineeDao.save(newTrainee);
				return getTraineeById(newTrainee.getTraineeId());
			} else {
				log.error("Error trying to create a new user.");
				return null;
			}
		} catch (Exception e) {
			log.error("Error trying to create a new trainee.", e);
			return null;
		}
	}
	
	@Override
	public TraineeBasicDto updateTrainee(TraineeUpdate update) {
		try {
			TraineeBasicDto original = getTraineeByUsername(update.getUsername());
			TraineeBasicDto updated = traineeMapper.parseUpdateToBasic(original, update);
			UserEntity user = userMapper.mapDtoToUser(updated);
			if (user != null) {
				TraineeEntity newTrainee = new TraineeEntity(
						original.getTraineeId(),
						updated.getDateOfBirth(),
						updated.getAddress(),
						user,
						original.getTrainers()
						.stream()
						.map(x -> {
							TrainerEntity y = new TrainerEntity(x, null, null, null);
							return y;
						}).collect(Collectors.toList()));
				userDao.save(user);
				traineeDao.save(newTrainee);
				return getTraineeById(newTrainee.getTraineeId());
			} else {
				log.error("Error trying to update an user.");
				return null;
			}
		} catch (Exception e) {
			log.error("Error trying to update a trainee.", e);
			return null;
		}
	}
	
	@Override
	public boolean deleteTrainee(UUID id) {
		try {
			TraineeBasicDto trainee = getTraineeById(id);
			traineeDao.deleteById(id);
			userDao.deleteById(trainee.getId());
			return true;
		} catch (Exception e) {
			log.error("Error trying to delete the trainee.", e);
			return false;
		}
	}
	
	@Override
	public boolean changeTraineePassword(String username, String newPasword) {
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
	
	private TraineeBasicDto mapTraineeToDto(TraineeEntity trainee) {
		try {
			if (trainee != null) {
				TraineeBasicDto dto = new TraineeBasicDto();
				UserEntity user = userDao.findById(trainee.getUserId().getId());
				dto.setId(user.getId());
				dto.setFirstName(user.getFirstName());
				dto.setLastName(user.getLastName());
				dto.setUsername(user.getUsername());
				dto.setPassword(user.getPassword());
				dto.setActive(user.getIsActive());
				dto.setTraineeId(trainee.getTraineeId());
				dto.setAddress(trainee.getAddress());
				dto.setDateOfBirth(trainee.getDateOfBirth());
				dto.setTrainers(trainee.getTrainers()
						.stream()
						.map(TrainerEntity::getTrainerId)
						.collect(Collectors.toList()));
				return dto;
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error("Error mapping trainee to DTO.", e);
			return null;
		}
	}
	
	/**
	 * Map a list of trainees to a list of DTOs of trainee.
	 * @param trainees
	 * 		- List of trainees to map.
	 * @return List of DTOs of trainee.
	 */
	private List<TraineeBasicDto> parseTraineesList(List<TraineeEntity> trainees) {
		try {
			List<TraineeBasicDto> dtoList = new ArrayList<>();
			trainees.stream().forEach(t -> {
				TraineeBasicDto dto = mapTraineeToDto(t);
				dtoList.add(dto);
			});
			return dtoList;
		} catch (Exception e) {
			log.error("Error parsing the trainees list to DTO.", e);
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
	public void setTraineeMapper(TraineeMapper traineeMapper) {
		this.traineeMapper = traineeMapper;
	}
	
}
