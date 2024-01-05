package com.epam.projects.gym.facades;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.projects.gym.dto.basics.TrainerBasicDto;
import com.epam.projects.gym.dto.fulls.TrainerFullDto;
import com.epam.projects.gym.dto.requests.TrainerRegister;
import com.epam.projects.gym.dto.requests.TrainerUpdate;
import com.epam.projects.gym.dto.responses.TrainerProfile;
import com.epam.projects.gym.dto.responses.TrainerUpdated;
import com.epam.projects.gym.dto.responses.UserCreated;
import com.epam.projects.gym.mappers.TraineeMapper;
import com.epam.projects.gym.mappers.TrainerMapper;
import com.epam.projects.gym.services.TraineeService;
import com.epam.projects.gym.services.TrainerService;

@Service
public class TrainerFacadeImpl implements TrainerFacade {
	
	private TrainerService trainerService;
	
	private TraineeService traineeService;
	
	@Autowired
	private TrainerMapper trainerMapper;
	
	@Autowired
	private TraineeMapper traineeMapper;
	
	@Autowired
	public TrainerFacadeImpl(TrainerService trainerService, TraineeService traineeService) {
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
