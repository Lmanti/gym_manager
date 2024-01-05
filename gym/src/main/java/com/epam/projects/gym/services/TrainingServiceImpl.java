package com.epam.projects.gym.services;

import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.projects.gym.dto.basics.TrainingBasicDto;
import com.epam.projects.gym.dto.requests.TrainingCreate;
import com.epam.projects.gym.mappers.TrainingMapper;
import com.epam.projects.gym.models.Training;
import com.epam.projects.gym.models.User;
import com.epam.projects.gym.repositories.TraineeDao;
import com.epam.projects.gym.repositories.TrainerDao;
import com.epam.projects.gym.repositories.TrainingDao;
import com.epam.projects.gym.repositories.TrainingTypeDao;
import com.epam.projects.gym.repositories.UserDao;

@Service
public class TrainingServiceImpl implements TrainingService {
	
	/**
	 * Logger for TrainingServiceImpl.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);
	
	/**
	 * Trainee DAO.
	 */
	@Autowired
	private TrainingDao trainingDao;
	
	@Autowired
	private TraineeDao traineeDao;
	
	@Autowired
	private TrainerDao trainerDao;
	
	@Autowired
	private TrainingTypeDao trainingTypeDao;
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * Training mapper.
	 */
	@Autowired
	private TrainingMapper mapper;

	@Override
	public List<TrainingBasicDto> getAllByIds(List<UUID> ids) {
		try {
			List<Training> trainings = trainingDao.findAll();
			return mapper.parseTrainings(trainings)
					.stream()
					.filter(x -> ids.contains(x.getId()))
					.collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Error trying to retrieve all trainings info.", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<TrainingBasicDto> getAllTraining() {
		try {
			List<Training> trainings = trainingDao.findAll();
			return mapper.parseTrainings(trainings);
		} catch (Exception e) {
			logger.error("Error trying to retrieve all trainings info.", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public TrainingBasicDto getById(UUID id) {
		try {
			Training training = trainingDao.findById(id);
			return mapper.mapIdToTraining(training);
		} catch (Exception e) {
			logger.error("Error trying to retrieve a training info with ID: " + id, e);
			return null;
		}
	}
	
	@Override
	public boolean createTraining(TrainingCreate training) {
		try {
			if (mapper.validateCreate(training)) {
				TrainingBasicDto dto = mapTraining(training);
				if (dto != null) {
					Training newTraining = mapper.mapDtoToTraining(dto);
					if (newTraining != null) {
						trainingDao.save(newTraining);
						TrainingBasicDto response = getById(newTraining.getId());
						return response != null;
					} else {
						logger.error("Error mapping the response, please check the logs.");
						return false;
					}
				} else {
					logger.error("Could not find a trainer or trainee with incoming usernames.");
					return false;
				}
			} else {
				logger.error("Training creation form cannot have null or empty fields.");
				return false;
			}
		} catch (Exception e) {
			logger.error("Error trying to create a new training.", e);
			return false;
		}
	}
	
	private TrainingBasicDto mapTraining(TrainingCreate training) {
		TrainingBasicDto dto = new TrainingBasicDto();
		User userTrainee = userDao.findByUsername(training.getTraineeUsername());
		User userTrainer = userDao.findByUsername(training.getTrainerUsername());
		if (userTrainee != null && userTrainer != null) {
			dto.setTraineeId(traineeDao.findByUserId(userTrainee.getId()).getId());
			dto.setTrainerId(trainerDao.findByUserId(userTrainer.getId()).getId());
			dto.setName(training.getTrainingName());
			dto.setTrainingTypeId(trainingTypeDao.findByName(training.getTrainingType()).getId());
			dto.setTrainingDate(Date.from(training.getTrainingDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			dto.setDuration(training.getDuration());
			return dto;
		} else {
			return null;
		}
	}

}
