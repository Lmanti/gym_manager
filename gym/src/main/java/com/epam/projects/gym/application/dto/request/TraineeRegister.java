package com.epam.projects.gym.application.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class TraineeRegister extends UserRegister {

	@ApiModelProperty(example = "2011-11-11")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;

	@NotBlank
	private String address;
	
	public TraineeUpdate getSettingUsername(String username) {
		TraineeUpdate trainee = new TraineeUpdate();
		trainee.setFirstName(getFirstName());
		trainee.setLastName(getLastName());
		trainee.setUsername(username);
		trainee.setDateOfBirth(dateOfBirth);
		trainee.setAddress(address);
		return trainee;
	}

}
