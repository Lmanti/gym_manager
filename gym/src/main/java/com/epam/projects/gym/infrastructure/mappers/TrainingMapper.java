package com.epam.projects.gym.infrastructure.mappers;

import java.util.List;

import com.epam.projects.gym.application.dto.basics.TrainingBasicDto;
import com.epam.projects.gym.application.dto.requests.TrainingCreate;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainingEntity;

public interface TrainingMapper {
	
	public TrainingBasicDto mapIdToTraining(TrainingEntity training);
	
	public List<TrainingBasicDto> parseTrainings(List<TrainingEntity> trainings);
	
	public TrainingEntity mapDtoToTraining(TrainingBasicDto training);
	
	public boolean validateCreate(TrainingCreate training);

}
