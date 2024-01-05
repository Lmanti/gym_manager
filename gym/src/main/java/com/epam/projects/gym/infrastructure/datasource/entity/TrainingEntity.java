package com.epam.projects.gym.infrastructure.datasource.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class TrainingEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "uuid")
	@JsonProperty("trainingId")
	private UUID trainingId;
	
	@ManyToOne
    @JoinColumn(name = "traineeId")
	@JsonProperty("traineeId")
	private TraineeEntity traineeId;
	
	@ManyToOne
    @JoinColumn(name = "trainerId")
	@JsonProperty("trainerId")
	private TrainerEntity trainerId;
	
	@NonNull
	@Column
	@JsonProperty("name")
	private String name;
	
	@ManyToOne
    @JoinColumn(name = "trainingTypeId")
	@JsonProperty("trainingTypeId")
	private TrainingTypeEntity trainingTypeId;

	@NonNull
	@Column
	@JsonProperty("trainingDate")
	private LocalDate trainingDate;

	@NonNull
	@Column
	@JsonProperty("duration")
	private Integer duration;

}
