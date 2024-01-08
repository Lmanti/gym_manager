package com.epam.projects.gym.application.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.epam.projects.gym.application.dto.TrainerAssignedDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraineeProfile extends GetProfileResponse {

	@ApiModelProperty(example = "2011-11-11")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;

	private String address;

	private List<TrainerAssignedDto> trainers;
	
}
