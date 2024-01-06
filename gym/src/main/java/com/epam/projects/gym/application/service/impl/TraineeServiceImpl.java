package com.epam.projects.gym.application.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.epam.projects.gym.application.dto.basic.TraineeBasicDto;
import com.epam.projects.gym.application.dto.request.TraineeRegister;
import com.epam.projects.gym.application.dto.request.TraineeUpdate;
import com.epam.projects.gym.application.dto.response.TraineeProfile;
import com.epam.projects.gym.application.dto.response.UserCreated;
import com.epam.projects.gym.application.mapper.TraineeMapper;
import com.epam.projects.gym.application.service.TraineeService;
import com.epam.projects.gym.domain.entity.Trainee;
import com.epam.projects.gym.domain.entity.User;
import com.epam.projects.gym.domain.repository.TraineeRepository;
import com.epam.projects.gym.domain.repository.UserRepository;
import com.epam.projects.gym.domain.utils.Randomizer;

@Service
public class TraineeServiceImpl implements TraineeService {
	
	private UserRepository userRepository;
	
	private TraineeRepository traineeRepository;
	
	public TraineeServiceImpl(UserRepository userRepository, TraineeRepository traineeRepository) {
		this.userRepository = userRepository;
		this.traineeRepository = traineeRepository;
	}

	@Override
	public List<TraineeProfile> getAllTrainees() {
		List<Trainee> trainees = traineeRepository.getAllTrainees();
		return trainees.stream().map(TraineeMapper::toProfile).collect(Collectors.toList());
	}

	@Override
	public TraineeBasicDto getTraineeById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TraineeProfile getTraineeByUsername(String username) {
		Trainee foundTrainee = traineeRepository.findByUsername(username);
		return TraineeMapper.toProfile(foundTrainee);
	}

	@Override
	public UserCreated createTrainee(TraineeRegister trainee) {
		try {
			
			boolean exist = userRepository.findByUsername(
					Randomizer.createUsername(
							trainee.getFirstName(), trainee.getLastName())) != null;
			
			User newUser = new User(
					null,
					trainee.getFirstName(),
					trainee.getLastName(),
					exist ? Randomizer.createUsername(trainee.getFirstName(), trainee.getLastName())
								.concat(Randomizer.getSerialNumber())
						: Randomizer.createUsername(trainee.getFirstName(), trainee.getLastName()),
					Randomizer.createPasword(trainee.getFirstName(), trainee.getLastName()),
					true,
					null,
					null);
			
			Trainee newTrainee = new Trainee(
					null,
					trainee.getDateOfBirth(),
					trainee.getAddress(),
					newUser,
					null);
			
			Trainee createdTrainee = traineeRepository.createTrainee(newTrainee);
	
			UserCreated response = new UserCreated();
			response.setUsername(createdTrainee.getUserId().getUsername());
			response.setPassword(createdTrainee.getUserId().getPassword());
			
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public TraineeBasicDto updateTrainee(TraineeUpdate update) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteTrainee(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<TraineeBasicDto> getAllByIds(List<UUID> trainees) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changeTraineePassword(String username, String newPasword) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
