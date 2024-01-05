package com.epam.projects.gym.application.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.projects.gym.application.dto.basics.TraineeBasicDto;
import com.epam.projects.gym.application.dto.fulls.TraineeFullDto;
import com.epam.projects.gym.application.dto.requests.TraineeRegister;
import com.epam.projects.gym.application.dto.requests.TraineeUpdate;
import com.epam.projects.gym.application.dto.responses.TraineeProfile;
import com.epam.projects.gym.application.dto.responses.TraineeUpdated;
import com.epam.projects.gym.application.dto.responses.UserCreated;
import com.epam.projects.gym.application.service.TraineeService;
import com.epam.projects.gym.infrastructure.adapter.TraineeAdapter;
import com.epam.projects.gym.infrastructure.adapter.TrainerAdapter;
import com.epam.projects.gym.infrastructure.mappers.TraineeMapper;
import com.epam.projects.gym.infrastructure.mappers.TrainerMapper;

@Service
public class TraineeServiceImpl implements TraineeService {
	
	private TraineeAdapter traineeService;
	
	private TrainerAdapter trainerService;
	
	@Autowired
	private TraineeMapper traineeMapper;
	
	@Autowired
	private TrainerMapper trainerMapper;
	
	@Autowired
	public TraineeServiceImpl(TraineeAdapter traineeService, TrainerAdapter trainerService) {
		this.traineeService = traineeService;
		this.trainerService = trainerService;
	}
	
	@Override
	public TraineeProfile getTraineeByUsername(String username) {
		TraineeBasicDto traineeBasic = traineeService.getTraineeByUsername(username);
		if (traineeBasic != null) {
			TraineeFullDto dto = getTraineeById(traineeBasic.getTraineeId());
			return traineeMapper.mapProfileResponse(dto);
		} else {
			return null;
		}
	}

	@Override
	public List<TraineeProfile> getAllTrainees() {
		List<TraineeBasicDto> basics = traineeService.getAllTrainees();
		if (basics != null && !basics.isEmpty()) {
			List<TraineeProfile> fulls = basics.stream()
					.map(x -> traineeMapper.mapProfileResponse(getTraineeById(x.getTraineeId())))
					.collect(Collectors.toList());
			return fulls;
		} else {
			return Collections.emptyList();			
		}
	}

	@Override
	public UserCreated createTrainee(TraineeRegister trainee) {
		TraineeBasicDto created = traineeService.createTrainee(trainee);
		if (created != null) {
			TraineeFullDto dto = getTraineeById(created.getTraineeId());
			return traineeMapper.mapCreationResponse(dto);
		} else {
			return null;
		}	
	}

	@Override
	public TraineeUpdated updateTrainee(TraineeUpdate trainee) {
		TraineeBasicDto updated = traineeService.updateTrainee(trainee);
		if (updated != null) {
			TraineeFullDto dto = getTraineeById(updated.getTraineeId());
			return traineeMapper.mapUpdateResponse(dto);
		} else {
			return null;
		}	
	}

	@Override
	public boolean deleteTraineeById(String id) {
		return traineeService.deleteTrainee(UUID.fromString(id));
	}

	@Override
	public boolean deleteTraineeByUsername(String username) {
		TraineeBasicDto traineeBasic = traineeService.getTraineeByUsername(username);
		if (traineeBasic != null) {
			return traineeService.deleteTrainee(traineeBasic.getTraineeId());
		} else {
			return false;
		}
	}
	
	private TraineeFullDto getTraineeById(UUID traineeId) {
		TraineeBasicDto basic = traineeService.getTraineeById(traineeId);
		if (basic != null) {
			TraineeFullDto full = traineeMapper.mapBasicToFull(basic);
			if (basic.getTrainers() != null && !basic.getTrainers().isEmpty()) {
				full.setTrainers(
						trainerMapper.mapBasicsToAssigned(
							trainerService.getAllByIds(basic.getTrainers())));
			} else {
				full.setTrainers(Collections.emptyList());
			}
			return full;
		} else {
			return null;
		}
	}
}
