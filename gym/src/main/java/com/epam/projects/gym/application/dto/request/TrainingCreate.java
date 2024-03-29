package com.epam.projects.gym.application.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.epam.projects.gym.domain.enums.Specialization;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TrainingCreate {

	@NotBlank
	private String traineeUsername;

	@NotBlank
	private String trainerUsername;

	@NotBlank
	private String trainingName;

	@NotNull
	@ApiModelProperty(example = "2011-11-11")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate trainingDate;

	@NotNull
	@ApiModelProperty(
            value = "Training type name",
            allowableValues = "Fitness, Yoga, Zumba, Stretching, Resistance"
    )
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Specialization trainingTypeName;

	@NotNull
	private int duration;

}
