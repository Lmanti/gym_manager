package com.epam.projects.gym.facades;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.projects.gym.dto.basics.TraineeBasicDto;
import com.epam.projects.gym.dto.fulls.TraineeFullDto;
import com.epam.projects.gym.dto.requests.TraineeRegister;
import com.epam.projects.gym.dto.requests.TraineeUpdate;
import com.epam.projects.gym.dto.responses.TraineeProfile;
import com.epam.projects.gym.dto.responses.TraineeUpdated;
import com.epam.projects.gym.dto.responses.UserCreated;
import com.epam.projects.gym.mappers.TraineeMapper;
import com.epam.projects.gym.mappers.TrainerMapper;
import com.epam.projects.gym.services.TraineeService;
import com.epam.projects.gym.services.TrainerService;

@Service
public class TraineeFacadeImpl implements TraineeFacade {
	
	private TraineeService traineeService;
	
	private TrainerService trainerService;
	
	@Autowired
	private TraineeMapper traineeMapper;
	
	@Autowired
	private TrainerMapper trainerMapper;
	
	@Autowired
	public TraineeFacadeImpl(TraineeService traineeService, TrainerService trainerService) {
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
