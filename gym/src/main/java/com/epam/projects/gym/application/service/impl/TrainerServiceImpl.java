package com.epam.projects.gym.application.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.projects.gym.application.dto.basics.TrainerBasicDto;
import com.epam.projects.gym.application.dto.fulls.TrainerFullDto;
import com.epam.projects.gym.application.dto.requests.TrainerRegister;
import com.epam.projects.gym.application.dto.requests.TrainerUpdate;
import com.epam.projects.gym.application.dto.responses.TrainerProfile;
import com.epam.projects.gym.application.dto.responses.TrainerUpdated;
import com.epam.projects.gym.application.dto.responses.UserCreated;
import com.epam.projects.gym.application.service.TrainerService;
import com.epam.projects.gym.infrastructure.adapter.TraineeAdapter;
import com.epam.projects.gym.infrastructure.adapter.TrainerAdapter;
import com.epam.projects.gym.infrastructure.mappers.TraineeMapper;
import com.epam.projects.gym.infrastructure.mappers.TrainerMapper;

@Service
public class TrainerServiceImpl implements TrainerService {
	
	private TrainerAdapter trainerService;
	
	private TraineeAdapter traineeService;
	
	@Autowired
	private TrainerMapper trainerMapper;
	
	@Autowired
	private TraineeMapper traineeMapper;
	
	@Autowired
	public TrainerServiceImpl(TrainerAdapter trainerService, TraineeAdapter traineeService) {
		this.trainerService = trainerService;
		this.traineeService = traineeService;
	}

	@Override
	public UserCreated createTrainer(TrainerRegister trainer) {
		if (trainerMapper.validateRegister(trainer)) {
			TrainerBasicDto created = trainerService.createTrainer(trainer);
			if (created != null) {
				TrainerFullDto dto = getTrainerById(created.getTrainerId());
				return trainerMapper.mapCreationResponse(dto);
			} else {
				return null;
			}
		} else {
			return null;
		}		
	}

	@Override
	public TrainerProfile getTrainerByUsername(String username) {
		TrainerBasicDto trainerBasic = trainerService.getTrainerByUsername(username);
		if (trainerBasic != null) {
			TrainerFullDto dto = getTrainerById(trainerBasic.getTrainerId());
			return trainerMapper.mapProfileResponse(dto);
		} else {
			return null;
		}
	}

	@Override
	public List<TrainerProfile> getAllTrainers() {
		List<TrainerBasicDto> basics = trainerService.getAllTrainers();
		if (basics != null && !basics.isEmpty()) {
			List<TrainerProfile> fulls = basics.stream()
					.map(x -> trainerMapper.mapProfileResponse(getTrainerById(x.getTrainerId())))
					.collect(Collectors.toList());
			return fulls;
		} else {
			return Collections.emptyList();			
		}
	}

	@Override
	public TrainerUpdated updateTrainer(TrainerUpdate trainer) {
		TrainerBasicDto updated = trainerService.updateTrainer(trainer);
		if (updated != null) {
			TrainerFullDto dto = getTrainerById(updated.getTrainerId());
			return trainerMapper.mapUpdateResponse(dto);
		} else {
			return null;
		}
	}
	
	private TrainerFullDto getTrainerById(UUID trainerId) {
		TrainerBasicDto basic = trainerService.getTrainerById(trainerId);
		if (basic != null) {
			TrainerFullDto full = trainerMapper.mapBasicToFull(basic);
			if (basic.getTrainees() != null && !basic.getTrainees().isEmpty()) {
				full.setTrainees(
						traineeMapper.mapBasicsToAssigned(
							traineeService.getAllByIds(basic.getTrainees())));
			} else {
				full.setTrainees(Collections.emptyList());
			}
			return full;
		} else {
			return null;
		}
	}

}
