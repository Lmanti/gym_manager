package com.epam.projects.gym.application.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import com.epam.projects.gym.domain.enums.Specialization;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainingCreate {

	@NotBlank
	private String traineeUsername;

	@NotBlank
	private String trainerUsername;

	@NotBlank
	private String trainingName;

	@NotBlank
	@ApiModelProperty(example = "2011-11-11")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate trainingDate;

	@NotBlank
	@ApiModelProperty(
            value = "Training type name",
            allowableValues = "Fitness, Yoga, Zumba, Stretching, Resistance"
    )
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Specialization trainingTypeName;

	@NotBlank
	private int duration;

}
