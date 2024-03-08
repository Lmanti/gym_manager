package com.epam.projects.gym.application.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.epam.projects.gym.application.dto.response.TrainingTypeResponse;
import com.epam.projects.gym.application.mapper.TrainingTypeMapper;
import com.epam.projects.gym.application.service.TrainingTypeService;
import com.epam.projects.gym.domain.entity.TrainingType;
import com.epam.projects.gym.domain.repository.TrainingTypeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {
	
	private TrainingTypeRepository trainingTypeRepository;
	
	public TrainingTypeServiceImpl(TrainingTypeRepository trainingTypeRepository) {
		this.trainingTypeRepository = trainingTypeRepository;
	}

	@Override
	public List<TrainingTypeResponse> getAllTrainingTypes() {
		log.info("Fetching all training types data.");
		List<TrainingType> trainingTypes = trainingTypeRepository.getAllTrainingTypes();
		log.info("training types data fetched successfully.");
		return trainingTypes.stream().map(TrainingTypeMapper::toResponse).collect(Collectors.toList());
	}

}
