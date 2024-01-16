package com.epam.projects.gym.application.dto.request;

import java.time.LocalDate;

import com.epam.projects.gym.application.enums.Specialization;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainingCreate {

	private String traineeUsername;

	private String trainerUsername;

	private String trainingName;

	@ApiModelProperty(example = "2011-11-11")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate trainingDate;

	@ApiModelProperty(
            value = "Training type name",
            allowableValues = "Fitness, Yoga, Zumba, Stretching, Resistance"
    )
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Specialization trainingTypeName;

	private int duration;

}
