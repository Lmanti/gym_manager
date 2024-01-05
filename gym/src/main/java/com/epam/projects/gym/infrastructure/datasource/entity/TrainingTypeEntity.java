package com.epam.projects.gym.infrastructure.datasource.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.epam.projects.gym.domain.entity.TrainingType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrainingTypeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "uuid")
	@JsonProperty("trainingTypeId")
	private UUID trainingTypeId;

	@NonNull
	@Column
	@JsonProperty("name")
	private String name;
	
	@OneToOne(mappedBy = "specialization")
	@JsonProperty("trainerId")
    private TrainerEntity trainerId;

	public TrainingType getInfo() {
		return new TrainingType(trainingTypeId, name);
	}

}
