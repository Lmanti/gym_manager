package com.epam.projects.gym.mappers;

import java.util.List;

import com.epam.projects.gym.dto.basics.TrainingBasicDto;
import com.epam.projects.gym.dto.requests.TrainingCreate;
import com.epam.projects.gym.models.Training;

public interface TrainingMapper {
	
	public TrainingBasicDto mapIdToTraining(Training training);
	
	public List<TrainingBasicDto> parseTrainings(List<Training> trainings);
	
	public Training mapDtoToTraining(TrainingBasicDto training);
	
	public boolean validateCreate(TrainingCreate training);

}
