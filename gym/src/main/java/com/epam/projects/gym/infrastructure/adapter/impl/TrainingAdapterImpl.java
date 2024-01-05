package com.epam.projects.gym.infrastructure.adapter.impl;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.projects.gym.application.dto.basics.TrainingBasicDto;
import com.epam.projects.gym.application.dto.requests.TrainingCreate;
import com.epam.projects.gym.infrastructure.adapter.TrainingAdapter;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainingEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.UserEntity;
import com.epam.projects.gym.infrastructure.datasource.repository.TraineeRepository;
import com.epam.projects.gym.infrastructure.datasource.repository.TrainerRepository;
import com.epam.projects.gym.infrastructure.datasource.repository.TrainingRepository;
import com.epam.projects.gym.infrastructure.datasource.repository.TrainingTypeRepository;
import com.epam.projects.gym.infrastructure.datasource.repository.UserRepository;
import com.epam.projects.gym.infrastructure.mappers.TrainingMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TrainingAdapterImpl implements TrainingAdapter {

	private TrainingRepository trainingDao;
	
	private TraineeRepository traineeDao;

	private TrainerRepository trainerDao;

	private TrainingTypeRepository trainingTypeDao;

	private UserRepository userDao;

	private TrainingMapper trainingMapper;

	public TrainingAdapterImpl(TrainingRepository trainingDao, TraineeRepository traineeDao,
			TrainerRepository trainerDao, TrainingTypeRepository trainingTypeDao, UserRepository userDao) {
		this.trainingDao = trainingDao;
		this.traineeDao = traineeDao;
		this.trainerDao = trainerDao;
		this.trainingTypeDao = trainingTypeDao;
		this.userDao = userDao;
	}

	@Override
	public List<TrainingBasicDto> getAllByIds(List<UUID> ids) {
		try {
			List<TrainingEntity> trainings = trainingDao.findAll();
			return trainingMapper.parseTrainings(trainings)
					.stream()
					.filter(x -> ids.contains(x.getId()))
					.collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Error trying to retrieve all trainings info.", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<TrainingBasicDto> getAllTraining() {
		try {
			List<TrainingEntity> trainings = trainingDao.findAll();
			return trainingMapper.parseTrainings(trainings);
		} catch (Exception e) {
			log.error("Error trying to retrieve all trainings info.", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public TrainingBasicDto getById(UUID id) {
		try {
			TrainingEntity training = trainingDao.findById(id);
			return trainingMapper.mapIdToTraining(training);
		} catch (Exception e) {
			log.error("Error trying to retrieve a training info with ID: " + id, e);
			return null;
		}
	}
	
	@Override
	public boolean createTraining(TrainingCreate training) {
		try {
			if (trainingMapper.validateCreate(training)) {
				TrainingBasicDto dto = mapTraining(training);
				if (dto != null) {
					TrainingEntity newTraining = trainingMapper.mapDtoToTraining(dto);
					if (newTraining != null) {
						trainingDao.save(newTraining);
						TrainingBasicDto response = getById(newTraining.getTrainingId());
						return response != null;
					} else {
						log.error("Error mapping the response, please check the logs.");
						return false;
					}
				} else {
					log.error("Could not find a trainer or trainee with incoming usernames.");
					return false;
				}
			} else {
				log.error("Training creation form cannot have null or empty fields.");
				return false;
			}
		} catch (Exception e) {
			log.error("Error trying to create a new training.", e);
			return false;
		}
	}
	
	private TrainingBasicDto mapTraining(TrainingCreate training) {
		TrainingBasicDto dto = new TrainingBasicDto();
		UserEntity userTrainee = userDao.findByUsername(training.getTraineeUsername());
		UserEntity userTrainer = userDao.findByUsername(training.getTrainerUsername());
		if (userTrainee != null && userTrainer != null) {
			dto.setTraineeId(traineeDao.findByUserId(userTrainee.getId()).getTraineeId());
			dto.setTrainerId(trainerDao.findByUserId(userTrainer.getId()).getTrainerId());
			dto.setName(training.getTrainingName());
			dto.setTrainingTypeId(trainingTypeDao.findByName(training.getTrainingType()).getTrainingTypeId());
			dto.setTrainingDate(training.getTrainingDate());
			dto.setDuration(training.getDuration());
			return dto;
		} else {
			return null;
		}
	}

	@Autowired
	public void setTrainingMapper(TrainingMapper trainingMapper) {
		this.trainingMapper = trainingMapper;
	}

}
