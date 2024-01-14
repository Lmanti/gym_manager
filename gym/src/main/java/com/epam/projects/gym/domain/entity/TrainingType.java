package com.epam.projects.gym.domain.entity;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class TrainingType {

	private UUID id;
	
	private String name;

}
