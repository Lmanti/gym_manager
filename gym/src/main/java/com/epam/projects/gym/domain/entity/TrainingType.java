package com.epam.projects.gym.domain.entity;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TrainingType {

	private UUID id;
	
	private String name;

}
