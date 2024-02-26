package com.epam.projects.gym.application.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerTraining {

	@NotBlank
	private String username;

	@ApiModelProperty(example = "2011-11-11")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate periodFrom;

	@ApiModelProperty(example = "2011-11-11")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate periodTo;

	private String traineeName;
	
}
