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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.epam.projects.gym.domain.entity.Training;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class TrainingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
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

	@Column(nullable = false)
	@JsonProperty("name")
	private String name;

	@ManyToOne
    @JoinColumn(name = "trainingTypeId")
	@JsonProperty("trainingTypeId")
	private TrainingTypeEntity trainingTypeId;

	@Column(nullable = false)
	@JsonProperty("trainingDate")
	private LocalDate trainingDate;

	@Column(nullable = false)
	@JsonProperty("duration")
	private Integer duration;
	
	public TrainingEntity(
			@NonNull TraineeEntity traineeId,
			@NonNull TrainerEntity trainerId,
			@NonNull String name,
			@NonNull TrainingTypeEntity trainingTypeId,
			@NonNull LocalDate trainingDate,
			@NonNull Integer duration
			) {
		this.traineeId = traineeId;
		this.trainerId = trainerId;
		this.name = name;
		this.trainingTypeId = trainingTypeId;
		this.trainingDate = trainingDate;
		this.duration = duration;
	}

	public Training toDomain() {
		Training training = new Training(
				traineeId.getBasicDomain(),
				trainerId.getBasicDomain(),
				name,
				trainingTypeId.toDomain(),
				trainingDate,
				duration);
		return training;
	}

}
