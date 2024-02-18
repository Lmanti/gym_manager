package com.epam.projects.gym.application.dto.request;

import javax.validation.constraints.NotBlank;

import com.epam.projects.gym.domain.enums.Specialization;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Trainer Registration Details")
@Getter
@Setter
public class TrainerRegister extends UserRegister {

	@NotBlank
	@ApiModelProperty(
            value = "Trainer's Specialization",
            allowableValues = "Fitness, Yoga, Zumba, Stretching, Resistance"
    )
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Specialization specialization;

	public TrainerUpdate getSettingUsername(String username) {
		TrainerUpdate trainer = new TrainerUpdate();
		trainer.setFirstName(getFirstName());
		trainer.setLastName(getLastName());
		trainer.setUsername(username);
		trainer.setSpecialization(specialization);
		return trainer;
	}

}
