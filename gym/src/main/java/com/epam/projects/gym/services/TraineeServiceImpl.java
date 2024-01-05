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

import com.epam.projects.gym.dto.basics.TraineeBasicDto;
import com.epam.projects.gym.dto.requests.TraineeRegister;
import com.epam.projects.gym.dto.requests.TraineeUpdate;
import com.epam.projects.gym.mappers.TraineeMapper;
import com.epam.projects.gym.mappers.UserMapper;
import com.epam.projects.gym.models.Trainee;
import com.epam.projects.gym.models.User;
import com.epam.projects.gym.repositories.TraineeDao;
import com.epam.projects.gym.repositories.UserDao;

/**
 * Trainee service.
 * @author lherreram
 */
@Service
public class TraineeServiceImpl implements TraineeService {
	
	/**
	 * Logger for TraineeServiceImpl.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);

	/**
	 * User DAO.
	 */
	@Autowired
	private UserDao userDao;
	
	/**
	 * Trainee DAO.
	 */
	@Autowired
	private TraineeDao traineeDao;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private TraineeMapper traineeMapper;
	

	@Override
	public List<TraineeBasicDto> getAllTrainees() {
		try {
			List<Trainee> trainees = traineeDao.findAll();
			return parseTraineesList(trainees);
		} catch (Exception e) {
			logger.error("Error trying to retrieve all trainees info.", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public TraineeBasicDto getTraineeById(UUID id) {
		try {
			Trainee trainee = traineeDao.findById(id);
			return mapTraineeToDto(trainee);
		} catch (Exception e) {
			logger.error("Error trying to retrieve all trainee info with ID: " + id, e);
			return null;
		}
	}
	
	@Override
	public List<TraineeBasicDto> getAllByIds(List<UUID> trainees) {
		try {
			List<Trainee> traineeList = traineeDao.findAll();
			return parseTraineesList(traineeList)
					.stream()
					.filter(x -> trainees.contains(x.getId()))
					.collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Error trying to retrieve all trainees info.", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public TraineeBasicDto getTraineeByUsername(String username) {
		try {
			User user = userDao.findByUsername(username);
			if (user != null) {
				Trainee trainee = traineeDao.findByUserId(user.getId());
				return mapTraineeToDto(trainee);
			} else {
				logger.error("There're no trainees with username: {}", username);
				return null;
			}			
		} catch (Exception e) {
			logger.error("Error trying to retrieve all trainee info with username: " + username, e);
			return null;
		}
	}

	@Override
	public TraineeBasicDto createTrainee(TraineeRegister trainee) {
		try {
			TraineeBasicDto dto = traineeMapper.mapRequestToBasic(trainee);
			User newUser = userMapper.mapDtoToUser(dto);
			if (newUser != null) {
				if (userExist(newUser.getUsername())) {
					newUser.setUsername(newUser.getUsername().concat(userMapper.getSerialNumber()));
				}
				Trainee newTrainee = new Trainee(
						newUser.getId(),
						dto.getDateOfBirth(),
						dto.getAddress()
						);
				userDao.save(newUser);
				traineeDao.save(newTrainee);
				return getTraineeById(newTrainee.getId());
			} else {
				logger.error("Error trying to create a new user.");
				return null;
			}
		} catch (Exception e) {
			logger.error("Error trying to create a new trainee.", e);
			return null;
		}
	}
	
	@Override
	public TraineeBasicDto updateTrainee(TraineeUpdate update) {
		try {
			TraineeBasicDto original = getTraineeByUsername(update.getUsername());
			TraineeBasicDto updated = traineeMapper.parseUpdateToBasic(original, update);
			User user = userMapper.mapDtoToUser(updated);
			if (user != null) {
				Trainee newTrainee = new Trainee(
						original.getTraineeId(),
						user.getId(),
						updated.getDateOfBirth(),
						updated.getAddress()
						);
				userDao.save(user);
				traineeDao.save(newTrainee);
				return getTraineeById(newTrainee.getId());
			} else {
				logger.error("Error trying to update an user.");
				return null;
			}
		} catch (Exception e) {
			logger.error("Error trying to update a trainee.", e);
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
			logger.error("Error trying to delete the trainee.", e);
			return false;
		}
	}
	
	@Override
	public boolean changeTraineePassword(String username, String newPasword) {
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
	 * Maps a trainee to a DTO.
	 * @param trainee
	 * 		- Trainee to map.
	 * @return DTO of trainee.
	 */
	private TraineeBasicDto mapTraineeToDto(Trainee trainee) {
		try {
			if (trainee != null) {
				TraineeBasicDto dto = new TraineeBasicDto();
				User user = userDao.findById(trainee.getUserId());
				dto.setId(user.getId());
				dto.setFirstName(user.getFirstName());
				dto.setLastName(user.getLastName());
				dto.setUsername(user.getUsername());
				dto.setPassword(user.getPassword());
				dto.setActive(user.isActive());
				dto.setTraineeId(trainee.getId());
				dto.setAddress(trainee.getAddress());
				dto.setDateOfBirth(trainee.getDateOfBirth());
				dto.setTrainers(trainee.getTrainers());
				return dto;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Error mapping trainee to DTO.", e);
			return null;
		}
	}
	
	/**
	 * Map a list of trainees to a list of DTOs of trainee.
	 * @param trainees
	 * 		- List of trainees to map.
	 * @return List of DTOs of trainee.
	 */
	private List<TraineeBasicDto> parseTraineesList(List<Trainee> trainees) {
		try {
			List<TraineeBasicDto> dtoList = new ArrayList<>();
			trainees.stream().forEach(t -> {
				TraineeBasicDto dto = mapTraineeToDto(t);
				dtoList.add(dto);
			});
			return dtoList;
		} catch (Exception e) {
			logger.error("Error parsing the trainees list to DTO.", e);
			return Collections.emptyList();
		}
	}
	
	private boolean userExist(String username) {
		User exist = userDao.findByUsername(username);
		return exist !=  null;
	}
	
}
