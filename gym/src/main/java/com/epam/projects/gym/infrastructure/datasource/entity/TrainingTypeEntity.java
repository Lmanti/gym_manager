package com.epam.projects.gym.infrastructure.datasource.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.epam.projects.gym.domain.entity.TrainingType;
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
public class TrainingTypeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	@JsonProperty("trainingTypeId")
	private UUID trainingTypeId;
	
	@Column
	@JsonProperty("name")
	private String name;
	
	@OneToMany(mappedBy = "specialization")
	@JsonProperty("trainerId")
    private List<TrainerEntity> trainerId;
	
	@OneToMany(mappedBy = "trainingTypeId")
	@JsonProperty("trainingId")
	private List<TrainingEntity> trainingId;
	
	public TrainingTypeEntity(
			@NonNull UUID trainingTypeId,
			@NonNull String name
			) {
		this.trainingTypeId = trainingTypeId;
		this.name = name;
	}

	public TrainingType toDomain() {
		return new TrainingType(trainingTypeId, name);
	}

}
