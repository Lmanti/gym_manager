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

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Training entity.
 * @author lherreram
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrainingEntity implements Serializable {
	
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Trainer's ID.
	 */
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	@JsonProperty("trainingId")
	private UUID trainingId;
	
	/**
	 * Registered trainee for this training.
	 */
	@ManyToOne
    @JoinColumn(name = "traineeId")
	@JsonProperty("traineeId")
	private TraineeEntity traineeId;
	
	/**
	 * Designated trainer for this training.
	 */
	@ManyToOne
    @JoinColumn(name = "trainerId")
	@JsonProperty("trainerId")
	private TrainerEntity trainerId;
	
	/**
	 * Training name.
	 */
	@NonNull
	@Column
	@JsonProperty("name")
	private String name;
	
	/**
	 * Training type for this training.
	 */
	@ManyToOne
    @JoinColumn(name = "trainingTypeId")
	@JsonProperty("trainingTypeId")
	private TrainingTypeEntity trainingTypeId;
	
	/**
	 * Training date.
	 */
	@NonNull
	@Column
	@JsonProperty("trainingDate")
	private LocalDate trainingDate;
	
	/**
	 * Training duration.
	 */
	@NonNull
	@Column
	@JsonProperty("duration")
	private Integer duration;

}
